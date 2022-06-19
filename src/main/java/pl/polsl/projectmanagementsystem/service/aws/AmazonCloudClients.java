package pl.polsl.projectmanagementsystem.service.aws;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!local")
@Service("amazonClient")
@RequiredArgsConstructor
public class AmazonCloudClients implements AmazonClients {

    @Override
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard()
                .build();
    }


    @Override
    public AmazonSNS getAmazonSns() {
        return AmazonSNSAsyncClientBuilder.standard()
                .build();
    }

    @Override
    public AmazonSQS getAmazonSqs() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .build();
    }

}