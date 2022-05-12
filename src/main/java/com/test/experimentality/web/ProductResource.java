package com.test.experimentality.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.experimentality.service.FileUploadService;
import com.test.experimentality.service.ProductService;
import com.test.experimentality.service.dto.ProductDTO;
import com.test.experimentality.web.errors.BadRequestException;
import com.test.experimentality.web.input.ProductInput;
import com.test.experimentality.web.utils.HeaderUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * REST controller for managing {@link com.test.experimentality.domain.Product}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";

    private static final String applicationName = "testExperiemtality";

    private final ProductService productService;

    private final FileUploadService fileUploadService;

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createPost(@RequestParam("imageFront") MultipartFile imageFront,
                                                 @RequestParam("imageBack") MultipartFile imageBack,
                                                 @RequestPart ProductInput product) throws URISyntaxException, JsonProcessingException {
        log.debug("REST request to save Post : {}", product);
        if (product.getCountry().toLowerCase().equals("colombia") && product.getDiscountPercentage() > 50) {
            throw new BadRequestException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "En este pais no se permite tanto descuento");
        }
        if ((product.getCountry().toLowerCase().equals("chile") || product.getCountry().toLowerCase().equals("mexico")) && product.getDiscountPercentage() > 30) {
            throw new BadRequestException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "En este pais no se permite tanto descuento");
        }
        String imgFront= fileUploadService.uploadImages(imageFront);
        String imgBack= fileUploadService.uploadImages(imageBack);
        ProductDTO result = productService
                .save(ProductDTO.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .discountPercentage(product.getDiscountPercentage())
                        .discountPrice(new BigDecimal(String.valueOf(product.getPrice().multiply(new BigDecimal(product.getDiscountPercentage())).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP))))
                        .country(product.getCountry())
                        .imageFront("http://localhost:8080/" + "uploads/" + imgFront)
                        .imageBack("http://localhost:8080/" + "uploads/" + imgBack)
                        .build());
        return ResponseEntity.created(new URI("/api/products/" + ""))
                .headers(HeaderUtils.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ""))
                .body(result);
    }

    /*@GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts(PostCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Posts by criteria: {}", criteria);
        Page<PostDTO> page = postQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/
}
