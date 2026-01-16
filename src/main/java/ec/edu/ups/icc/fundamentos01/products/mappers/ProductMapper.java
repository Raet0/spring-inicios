package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

public class ProductMapper {

    // Dominio -> ResponseDto
    public static ProductResponseDto toResponse(Product product) {
        if (product == null)
            return null;
        return product.toResponseDto();
    }

    // DTO -> Dominio
    public static Product fromCreateDto(CreateProductDto dto) {
        if (dto == null)
            return null;

        String name = (dto.name != null && !dto.name.isBlank()) ? dto.name : "Producto sin nombre";
        Double price = dto.price; 
        Integer stock = dto.stock; 

        return new Product(null, name, price, stock, null);  // ⭐ null (Long) en vez de 0 (int)
    }

    public static Product fromUpdateDto(UpdateProductDto dto, Product existing) {
        if (dto == null || existing == null)
            return existing;
        existing.update(dto);
        return existing;
    }

    public static Product fromPartialDto(PartialUpdateProductDto dto, Product existing) {
        if (dto == null || existing == null)
            return existing;
        existing.partialUpdate(dto);
        return existing;
    }

    // Entidad -> Dominio
    public static Product fromEntity(ProductEntity entity) {
        if (entity == null)
            return null;

        String name = (entity.getName() != null && !entity.getName().isBlank())
                ? entity.getName()
                : "Producto sin nombre";
        Double price = (entity.getPrice() != null && entity.getPrice() >= 0)
                ? entity.getPrice()
                : 0.0;
        Integer stock = (entity.getStock() != null && entity.getStock() >= 0)
                ? entity.getStock()
                : 0;

        return new Product(
                entity.getId(),  
                name,
                price,
                stock,
                entity.getCreatedAt());
    }

    // Dominio -> Entidad
    public static ProductEntity toEntity(Product product) {
        if (product == null)
            return null;
        return product.toEntity();
    }
}