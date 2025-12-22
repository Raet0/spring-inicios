package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntitie;

public class ProductMapper {

    public static ProductEntitie toEntity(int id, String name, int cant) {
        return new ProductEntitie(id, name, cant);
    }

    public static ProductResponseDto toResponse(ProductEntitie product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.cant = product.getCant();
        return dto;
    }
}
