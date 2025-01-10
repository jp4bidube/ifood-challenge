package jp.projects.ifood_challenge.domain.product;

import jp.projects.ifood_challenge.domain.category.Category;
import jp.projects.ifood_challenge.domain.category.CategoryDTO;
import org.json.JSONObject;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.UUID;

@DynamoDbBean
public class Product {
    private UUID id;
    private String ownerId;
    private String title;
    private String description;
    private Integer price;
    private String category;

    public static Product fromDTO(ProductDTO productDTO) {
        var entity = new Product();

        entity.setId(UUID.randomUUID());
        entity.setOwnerId(productDTO.ownerId());
        entity.setTitle(productDTO.title());
        entity.setDescription(productDTO.description());
        entity.setPrice(productDTO.price());
        entity.setCategory(productDTO.categoryId());

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

    @DynamoDbAttribute("category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @DynamoDbAttribute("price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("title", this.title);
        jsonObject.put("description", this.description);
        jsonObject.put("ownerId", this.ownerId);
        jsonObject.put("category", this.category);
        jsonObject.put("price", this.price);
        jsonObject.put("type", "product");

        return jsonObject.toString();
    }
}
