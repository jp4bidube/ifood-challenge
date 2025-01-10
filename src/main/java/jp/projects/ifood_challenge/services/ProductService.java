package jp.projects.ifood_challenge.services;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import jp.projects.ifood_challenge.domain.category.exceptions.CategoryNotFoundException;
import jp.projects.ifood_challenge.domain.product.Product;
import jp.projects.ifood_challenge.domain.product.ProductDTO;
import jp.projects.ifood_challenge.domain.product.exceptions.ProductNotFoundException;
import jp.projects.ifood_challenge.services.aws.SnsNotificationService;
import jp.projects.ifood_challenge.services.aws.dto.MessageDTO;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final DynamoDbTemplate dynamoDbTemplate;
    private final CategoryService categoryService;
    private final SnsNotificationService snsNotificationService;

    public ProductService(DynamoDbTemplate dynamoDbTemplate, CategoryService categoryService, SnsNotificationService snsNotificationService) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.categoryService = categoryService;
        this.snsNotificationService = snsNotificationService;
    }

    public Product insert(ProductDTO productDTO) {
        var category = this.categoryService.getById(productDTO.categoryId()).orElseThrow(CategoryNotFoundException::new);

        var newProduct = Product.fromDTO(productDTO);

        dynamoDbTemplate.save(newProduct);

        this.snsNotificationService.publish(new MessageDTO(newProduct.toString()));

        return newProduct;
    }

    public Product update(String id, ProductDTO productDTO) {
        var entity = Optional.ofNullable(dynamoDbTemplate.load(Key.builder()
                .partitionValue(id)
                .build(), Product.class)).orElseThrow(ProductNotFoundException::new);

        if (productDTO.categoryId() != null) {
            this.categoryService.getById(productDTO.categoryId()).orElseThrow(CategoryNotFoundException::new);
            entity.setCategory(productDTO.categoryId());
        }
        if (!productDTO.title().isEmpty()) entity.setTitle(productDTO.title());
        if (!productDTO.description().isEmpty()) entity.setDescription(productDTO.description());
        if (!(productDTO.price() == null)) entity.setPrice(productDTO.price());

        dynamoDbTemplate.update(entity);

        this.snsNotificationService.publish(new MessageDTO(entity.toString()));

        return entity;
    }

    public List<Product> getAll() {
        return dynamoDbTemplate.scanAll(Product.class).items().stream().toList();
    }

    public void delete(String id) {
        var entity = Optional.ofNullable(dynamoDbTemplate.load(Key.builder()
                .partitionValue(id)
                .build(), Product.class)).orElseThrow(ProductNotFoundException::new);

        dynamoDbTemplate.delete(entity);
    }
}
