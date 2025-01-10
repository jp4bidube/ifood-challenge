package jp.projects.ifood_challenge.config.aws.sns;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.Topic;

import java.net.URI;

@Configuration
public class SnsConfig {

    @Bean
    public SnsClient snsAsyncClient() {
        return SnsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.SA_EAST_1)
                .build();
    }

    @Bean(name = "catalogEventsTopic")
    public String catalogEventsTopic() {
        return "arn:aws:sns:sa-east-1:000000000000:catalog-emit";
    }

}