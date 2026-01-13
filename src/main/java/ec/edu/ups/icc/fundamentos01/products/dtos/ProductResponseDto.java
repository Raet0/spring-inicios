package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;

public class ProductResponseDto {

    public Long id;
    public String name;
    public Double price;
    public String description;

    // ============== OBJETOS ANIDADOS ==============
    
    public UserSummaryDto user;
    public CategoryResponseDto category;

    // ============== AUDITORÍA ==============
    
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    // ============== DTOs INTERNOS ==============
    
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