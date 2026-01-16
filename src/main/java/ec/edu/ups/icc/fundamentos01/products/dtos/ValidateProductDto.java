package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.NotBlank;

public class ValidateProductDto {

    public Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    public String name;

}