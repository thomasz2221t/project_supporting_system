package pl.polsl.projectmanagementsystem.service.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.polsl.projectmanagementsystem.config.AmazonConfigValues;

@Profile("local")
@Service("amazonClient")
@RequiredArgsConstructor
public class AmazonLocalClients implements AmazonClients {

    private final AmazonConfigValues amazonConfigValues;

    @Override
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(getEndpointConfiguration())
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("foo", "bar")))
                .build();
    }

    @Override
    public AmazonSNS getAmazonSns() {
        return AmazonSNSClientBuilder
                .standard()
                .withEndpointConfiguration(getEndpointConfiguration())
                .build();
    }

    @Override
    public AmazonSQS getAmazonSqs() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(getEndpointConfiguration())
                .build();
    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(amazonConfigValues.getLocalstackSQSUrl(), amazonConfigValues.getRegionStatic());
    }

}