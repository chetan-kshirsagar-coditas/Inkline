package com.mukesh.inkLine.service;

import com.mukesh.inkLine.entities.Attachments;
import com.mukesh.inkLine.exceptions.S3Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@Slf4j
public class S3Service {
    private final S3Client s3Client;
    private final String bucketName;

    S3Service(
            S3Client s3Client,
            @Value("${aws.bucket.name}")
            String bucketName
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file, String key) {
        try {
            log.info("Uploading the file.");
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );
            log.info("Uploaded the file");
        } catch (IOException e) {
            throw new S3Exception("There was an error while uploading the document to the S3 bucket.");
        }

        return "Document uploaded successfully.";
    }
}
