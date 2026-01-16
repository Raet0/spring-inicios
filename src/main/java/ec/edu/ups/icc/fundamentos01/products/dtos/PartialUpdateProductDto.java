package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * DTO para actualización parcial de un producto
 */
public class PartialUpdateProductDto {

    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    public Double price;

    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    public Integer stock;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    public String description;
}
