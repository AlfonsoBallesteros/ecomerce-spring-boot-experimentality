package com.test.experimentality.service.mapper;

import com.test.experimentality.domain.Product;
import com.test.experimentality.service.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

    default Product fromId(UUID id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
