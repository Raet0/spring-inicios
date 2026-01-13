package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateProductDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150)
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false)
    public Double price;

    @Size(max = 500)
    public String description;

    // ============== ACTUALIZACIÓN DE RELACIONES ==============

    @NotNull(message = "El ID de la categoría es obligatorio")
    public Long categoryId;

    // Nota: No se permite cambiar el owner de un producto una vez creado
    // Si fuera necesario, sería una operación de negocio especial

}
