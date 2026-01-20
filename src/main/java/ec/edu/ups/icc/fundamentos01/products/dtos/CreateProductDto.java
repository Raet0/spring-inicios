package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.Set;

import jakarta.validation.constraints.*;

public class CreateProductDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150)
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false)
    public Double price;

    @Size(max = 500)
    public String description;

    @Min(value = 0, message = "El stock no puede ser negativo")
    public Integer stock; 

    // ============== RELACIONES ==============

    @NotNull(message = "El ID del usuario es obligatorio")
    public Long userId;

    public Set<Long> categoryIds;
}