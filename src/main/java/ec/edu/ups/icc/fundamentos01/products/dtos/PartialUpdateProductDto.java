package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class PartialUpdateProductDto {

    @Size(min = 3, max = 150, message = "El nombre debe tener al menos 3 caracteres")
    public String name;

    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
    public Double price;

    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    public Integer stock;
}
