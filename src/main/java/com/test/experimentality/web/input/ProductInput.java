package com.test.experimentality.web.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductInput {

    private String name;

    private BigDecimal price;

    private Integer discountPercentage;

    private String country;
}
