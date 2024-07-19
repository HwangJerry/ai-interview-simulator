package soohyunj.interviewsimulator.infra;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class PresignedUrlGenerator {
    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;

    public String createFileUploadUrl(String fileUrl) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(s3Properties.bucket(), fileUrl)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getOneHourExpiration());
//        generatePresignedUrlRequest.addRequestParameter(
//                Headers.S3_CANNED_ACL,
//                CannedAccessControlList.PublicRead.toString()
//        );
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    public String createFileReadUrl(String fileUrl) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(s3Properties.bucket(), fileUrl)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(getOneHourExpiration());
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private Date getOneHourExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
