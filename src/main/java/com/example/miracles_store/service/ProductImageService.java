package com.example.miracles_store.service;

import com.example.miracles_store.entity.ProductImage;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public String addProductImage(MultipartFile file) throws IOException {
        ProductImage image = new ProductImage();

        image.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        image = productImageRepository.insert(image);
        return image.getId();
    }

    public ProductImage getProductImage(String id) {
        return productImageRepository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException("Can't find image with id: " + id));
    }

    public void deleteProductImage(String id) {
        productImageRepository.deleteById(id);
    }
}
