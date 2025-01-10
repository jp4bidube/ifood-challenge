package jp.projects.ifood_challenge.services;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import jp.projects.ifood_challenge.domain.category.Category;
import jp.projects.ifood_challenge.domain.category.CategoryDTO;
import jp.projects.ifood_challenge.domain.category.exceptions.CategoryNotFoundException;
import jp.projects.ifood_challenge.services.aws.SnsNotificationService;
import jp.projects.ifood_challenge.services.aws.dto.MessageDTO;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final  DynamoDbTemplate dynamoDbTemplate;
    private final SnsNotificationService snsNotificationService;

    public CategoryService(DynamoDbTemplate dynamoDbTemplate,SnsNotificationService snsNotificationService) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.snsNotificationService = snsNotificationService;
    }

    public Category insert(CategoryDTO categoryDTO) {
        var entity = Category.fromDTO(categoryDTO);
        dynamoDbTemplate.save(entity);
        this.snsNotificationService.publish(new MessageDTO(entity.toString()));
        return entity;
    }

    public Category update(String id, CategoryDTO categoryDTO) {
        var entity = this.getById(id).orElseThrow(CategoryNotFoundException::new);

        if (!categoryDTO.title().isEmpty()) entity.setTitle(categoryDTO.title());
        if (!categoryDTO.description().isEmpty()) entity.setDescription(categoryDTO.description());

        dynamoDbTemplate.update(entity);
        this.snsNotificationService.publish(new MessageDTO(entity.toString()));

        return entity;
    }

    public List<Category> getAll() {
        return dynamoDbTemplate.scanAll(Category.class).items().stream().toList();
    }

    public Optional<Category> getById(String id) {
        return Optional.ofNullable(dynamoDbTemplate.load(Key.builder()
                .partitionValue(id)
                .build(), Category.class));
    }

    public void delete(String id) {
        var entity = this.getById(id).orElseThrow(CategoryNotFoundException::new);

        dynamoDbTemplate.delete(entity);
    }

}
