// src/main/java/ec/edu/ups/icc/fundamentos01/products/dtos/ProductResponseDto.java
package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {

    public Long id;
    public String name;
    public Double price;
    public String description;  
    public Integer stock;
    // informacion de owner
    public UserSummaryDto user;  


    public CategoryResponseDto category;  

    public static class CategoryResponseDto {
        public Long id;
        public String name;
        public String description;
    }  
    // categorias (n:n) - lista de objetos

    public List<CategoryResponseDto> categories;

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;


    // dtos internos reutilizables
    public static class UserSummaryDto {
        public Long id;
        public String name;
        public String email;
    }
}