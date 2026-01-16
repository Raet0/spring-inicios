// src/main/java/ec/edu/ups/icc/fundamentos01/categories/dtos/UpdateCategoryDto.java
package ec.edu.ups.icc.fundamentos01.categories.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateCategoryDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    public String name;
    
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    public String description;
}