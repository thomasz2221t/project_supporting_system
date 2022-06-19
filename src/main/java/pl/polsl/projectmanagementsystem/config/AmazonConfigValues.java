package pl.polsl.projectmanagementsystem.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AmazonConfigValues {

    @Value("${cloud.aws.region.static:eu-central-1}")
    private String regionStatic;

    @Value("${cloud.localstack.sqs.url:}")
    private String localstackSQSUrl;
}