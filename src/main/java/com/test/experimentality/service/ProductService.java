package com.test.experimentality.service;

import com.test.experimentality.domain.Product;
import com.test.experimentality.repository.ProductRepository;
import com.test.experimentality.service.dto.ProductDTO;
import com.test.experimentality.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    /**
     * Save a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        ProductDTO result = productMapper.toDto(product);
        return result;
    }

    /**
     * Get all the products.
     *
     * @param
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(List<UUID> productIds) {
        log.debug("Request to get all Products");
        return productRepository.findAllById(productIds).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

}
