package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public class CreateProductDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 500, message = "La descripción debe tener entre 5 y 500 caracteres")
    public String description;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    public double price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    public int stock;
}
