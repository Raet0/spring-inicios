// src/main/java/ec/edu/ups/icc/fundamentos01/products/dtos/ProductResponseDto.java
package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;

public class ProductResponseDto {

    public Long id;
    public String name;
    public Double price;
    public String description;  
    public Integer stock;

    public UserSummaryDto user;  
    public CategoryResponseDto category;  

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public static class UserSummaryDto {
        public Long id;
        public String name;
        public String email;
    }

    public static class CategoryResponseDto {
        public Long id;
        public String name;
        public String description;
    }
}