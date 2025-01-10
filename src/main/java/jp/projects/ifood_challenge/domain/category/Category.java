package jp.projects.ifood_challenge.domain.category;

import org.json.JSONObject;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.UUID;

@DynamoDbBean
public class Category {

    private UUID id;
    private String ownerId;
    private String title;
    private String description;

    public static Category fromDTO(CategoryDTO categoryDTO) {
        var entity = new Category();

        entity.setId(UUID.randomUUID());
        entity.setOwnerId(categoryDTO.ownerId());
        entity.setTitle(categoryDTO.title());
        entity.setDescription(categoryDTO.description());

        return entity;
    }

    @DynamoDbAttribute("id")
    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDbAttribute("owner_id")
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @DynamoDbAttribute("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDbAttribute("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",this.id);
        jsonObject.put("title",this.title);
        jsonObject.put("description",this.description);
        jsonObject.put("ownerId",this.ownerId);
        jsonObject.put("type", "category");

        return jsonObject.toString();
    }
}
