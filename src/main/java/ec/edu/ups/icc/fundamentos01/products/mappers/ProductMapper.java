package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;

/**
 * Mapper para conversión entre Product y ProductResponseDto
 * Nota: Preferentemente usar Product.fromEntity() y toResponseDto en el servicio
 */
public class ProductMapper {

    public static ProductResponseDto toResponse(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.price = product.getPrice();
        dto.stock = product.getStock();
        dto.description = product.getDescription();
        return dto;
    }
}
