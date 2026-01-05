package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public class PartialUpdateProductDto {
    @Size(min = 3, max = 150)
    public String name;

    @Size(min = 5, max = 500)
    public String description;

    @DecimalMin(value = "0.01")
    public Double price;

    @Min(value = 0)
    public Integer stock;
}
