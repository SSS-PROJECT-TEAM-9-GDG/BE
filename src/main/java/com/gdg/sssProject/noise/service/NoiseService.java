package com.gdg.sssProject.noise.service;

import org.bytedeco.opencv.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Service
public class NoiseService {

    //노이즈 추가를 위해 가우시안 함수 사용!
    public byte[] applyGaussianNoise(MultipartFile file, String level) {

        try {
            // MultipartFile → BufferedImage 변환
            BufferedImage inputImage = ImageIO.read(file.getInputStream());
            if (inputImage == null) {
                throw new RuntimeException("이미지를 읽을 수 없습니다.");
            }

            // BufferedImage → OpenCV Mat 변환
            Mat src = bufferedImageToMat(inputImage);
            if (src.empty()) {
                throw new RuntimeException("이미지 파일을 불러올 수 없습니다.");
            }

            // 노이즈 강도 설정 (상, 중, 하)
            double mean = 0;
            double stddev;

            switch (level.toLowerCase()) {
                case "상":
                    stddev = 50;
                    break;
                case "중":
                    stddev = 30;
                    break;
                case "하":
                    stddev = 15;
                    break;
                default:
                    throw new IllegalArgumentException("잘못된 노이즈 강도입니다.");
            }

            Mat noise = new Mat(src.size(), src.type());

            Mat meanMat = new Mat(1, 1, CV_64F, new Scalar(mean));
            Mat stddevMat = new Mat(1, 1, CV_64F, new Scalar(stddev));

            randn(noise, meanMat, stddevMat);

            add(src, noise, src);

            // OpenCV Mat → BufferedImage 변환
            BufferedImage outputImage = matToBufferedImage(src);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "jpg", baos);

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("이미지 처리 실패", e);
        }
    }

    // BufferedImage → Mat 변환 함수
    private Mat bufferedImageToMat(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return imdecode(new Mat(bytes), IMREAD_COLOR);
        } catch (IOException e) {
            throw new RuntimeException("이미지를 Mat으로 변환하는 중 오류 발생", e);
        }
    }

    // Mat → BufferedImage 변환 함수
    private BufferedImage matToBufferedImage(Mat mat) {
        int type = (mat.channels() == 1) ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] data = new byte[mat.channels() * mat.cols() * mat.rows()];
        mat.data().get(data);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }
}

