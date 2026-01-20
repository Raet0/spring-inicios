package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {
    public Long id;
    public String name;
    public Double price;
    public String description;
    public Integer stock;

    // ============== INFORMACIÓN DEL OWNER ==============
    public UserSummaryDto user;

    // ============== CATEGORÍAS (N:N) - Lista de objetos ==============
    public List<CategorySummaryDto> categories;

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    // ============== DTOs INTERNOS REUTILIZABLES ==============
    
    public static class UserSummaryDto {
        public Long id;
        public String name;
        public String email;
    }

    public static class CategorySummaryDto { 
        public Long id;
        public String name;
        public String description;
    }
}