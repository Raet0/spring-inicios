package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta para productos
 * Estructura anidada para mejor semántica y usabilidad desde el frontend
 */
public class ProductResponseDto {

    public Long id;
    public String name;
    public Double price;
    public int stock;
    public String description;

    // ============== OBJETOS ANIDADOS ==============
    
    public UserSummaryDto user;
    public List<CategoryResponseDto> categories;

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
