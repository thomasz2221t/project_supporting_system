package pl.polsl.projectmanagementsystem.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import pl.polsl.projectmanagementsystem.utils.CloneUtils;

@Configuration
@RequiredArgsConstructor
public class AmazonSQSConfiguration {

    private final AmazonConfigValues amazonConfigValues;

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        MappingJackson2MessageConverter jackson2MessageConverter = new MappingJackson2MessageConverter();
        jackson2MessageConverter.setObjectMapper(CloneUtils.objectMapper());
        return jackson2MessageConverter;
    }

    @Profile("local")
    @Bean("amazonSQS")
    public AmazonSQSAsync amazonSQSLocal() {
        return AmazonSQSAsyncClientBuilder
                .standard()
                .withEndpointConfiguration(getEndpointConfiguration())
                .build();
    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(amazonConfigValues.getLocalstackSQSUrl(), amazonConfigValues.getRegionStatic());
    }
}