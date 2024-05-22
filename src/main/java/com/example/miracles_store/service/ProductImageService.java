package com.example.miracles_store.service;

import com.example.miracles_store.entity.ProductImage;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageService {

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;

    public String addProductImage(MultipartFile file) throws IOException {
        ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType());
        return id.toString();
    }

    public ProductImage getProductImage(String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        ProductImage image = new ProductImage();
        if (file != null) {
            image.setStream(operations.getResource(file).getInputStream());
        } else {
            throw new ObjectNotFoundException("Can't find image with id: " + id);
        }
        return image;
    }

    public void deleteProductImage(String id) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }
}
