package ec.edu.ups.icc.fundamentos01.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.models.Product;

public class ProductMapper {

    public static ProductResponseDto toResponse(Product product) {
        ProductResponseDto dto = new ProductResponseDto(
                product.getId(), 
                product.getName(), 
                product.getDescription(), 
                product.getPrice(),
                product.getStock());
        return dto;
    }

    public static Product fromCreateDto(CreateProductDto dto) {
        return new Product(dto.name, dto.description, dto.price, dto.stock);
    }
}

