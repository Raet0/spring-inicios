// src/main/java/ec/edu/ups/icc/fundamentos01/products/dtos/CreateProductDto.java
package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.*;

public class CreateProductDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    public Double price;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    public String description;  

    @Min(value = 0, message = "El stock no puede ser negativo")  // ⭐ Quité @NotNull
    public Integer stock;

    @NotNull(message = "El ID del usuario es obligatorio")
    public Long userId;  

    @NotNull(message = "El ID de la categoría es obligatorio")
    public Long categoryId;
}