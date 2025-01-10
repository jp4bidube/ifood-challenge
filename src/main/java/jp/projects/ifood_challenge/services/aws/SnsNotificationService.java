package jp.projects.ifood_challenge.services.aws;

import io.awspring.cloud.sns.core.SnsTemplate;
import jp.projects.ifood_challenge.services.aws.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;

@Service
public class SnsNotificationService {

    private final SnsTemplate snsTemplate;
    String catalogTopic;

    public SnsNotificationService(SnsTemplate snsTemplate, @Qualifier("catalogEventsTopic") String catalogTopic) {
        this.snsTemplate = snsTemplate;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO message) {
        snsTemplate.convertAndSend(catalogTopic, message);
    }
}
