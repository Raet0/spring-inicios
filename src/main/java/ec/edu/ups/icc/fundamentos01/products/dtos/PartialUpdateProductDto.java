package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.Set;

import jakarta.validation.constraints.*;

public class PartialUpdateProductDto {
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    public Double price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    public Integer stock;

    /**
     * Opcional: si viene, se reemplaza el set completo de categorías
     */
    public Set<Long> categoryIds;

    public Long ownerId;

}