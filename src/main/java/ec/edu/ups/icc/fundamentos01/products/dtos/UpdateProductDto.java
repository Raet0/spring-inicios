// src/main/java/ec/edu/ups/icc/fundamentos01/products/dtos/UpdateProductDto.java
package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.*;

public class UpdateProductDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150)
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false)
    public Double price;

    @Size(max = 500)
    public String description;
    
    @Min(value = 0)  
    public Integer stock;

    @NotNull(message = "El ID de la categoría es obligatorio")
    public Long categoryId;
}