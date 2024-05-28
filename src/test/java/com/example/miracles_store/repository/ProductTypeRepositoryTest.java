package com.example.miracles_store.repository;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTypeRepositoryTest {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Test
    @Sql("/sql/productTypes.sql")
    void existsByName() {
        Assertions.assertThat(productTypeRepository.existsByName("Shoes")).isTrue();
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void existsByNameAndIdNotEqual() {
        Assertions.assertThat(productTypeRepository.existsByNameAndIdNotEqual("Shoes",123)).isTrue();
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void findById() {
        ProductType productType = productTypeRepository.findById(2)
                .orElseThrow( () -> new ObjectNotFoundException("Can't find productType"));

        Assertions.assertThat(productType).isNotNull();
        Assertions.assertThat(productType.getName()).isEqualTo("T-shirts");
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void findAll() {
        Pageable pageable = PageRequest.of(0, 20);

        Page<ProductType> productTypes = productTypeRepository.findAll(pageable);

        Assertions.assertThat(productTypes).isNotNull();
        Assertions.assertThat(productTypes.getContent().get(0).getName()).isEqualTo("Shoes");
        Assertions.assertThat(productTypes.getContent().get(1).getName()).isEqualTo("T-shirts");
    }

    @Test
    void save() {
        ProductType productType = new ProductType();
        productType.setName("Denim");

        ProductType saved = productTypeRepository.save(productType);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getName()).isEqualTo(productType.getName());
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void update() {
        ProductType productType = new ProductType();
        productType.setId(1);
        productType.setName("Jeans");

        ProductType updated = productTypeRepository.saveAndFlush(productType);

        Assertions.assertThat(updated).isNotNull();
        Assertions.assertThat(updated.getName()).isEqualTo(productType.getName());
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void deleteById() {
        productTypeRepository.deleteById(2);

        Optional<ProductType> productType = productTypeRepository.findById(2);

        Assertions.assertThat(productType).isEmpty();
    }
}
