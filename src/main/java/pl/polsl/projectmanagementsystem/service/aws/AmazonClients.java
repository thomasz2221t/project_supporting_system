package pl.polsl.projectmanagementsystem.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQS;

public interface AmazonClients {

    AmazonS3 getAmazonS3();

    AmazonSNS getAmazonSns();

    AmazonSQS getAmazonSqs();
}