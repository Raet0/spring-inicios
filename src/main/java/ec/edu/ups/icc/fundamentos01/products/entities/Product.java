// src/main/java/ec/edu/ups/icc/fundamentos01/products/entities/Product.java
package ec.edu.ups.icc.fundamentos01.products.entities;

import java.time.LocalDateTime;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;

public class Product {

    private Long id; // ⭐ Cambiar de int a Long
    private String name;
    private Double price;
    private Integer stock;
    private LocalDateTime createdAt;

    // Constructor público para permitir instanciación desde mappers
    public Product(Long id, String name, Double price, Integer stock, LocalDateTime createdAt) {
        if (name == null || name.isBlank() || name.length() < 3)
            throw new IllegalArgumentException("Nombre inválido");
        if (price == null || price < 0)
            throw new IllegalArgumentException("Precio inválido");
        if (stock == null || stock < 0)
            throw new IllegalArgumentException("Stock inválido");

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    // Factory desde DTO de creación
    public static Product fromDto(CreateProductDto dto) {
        return new Product(null, dto.name, dto.price, dto.stock, null); // ⭐ null en vez de 0
    }

    // Factory desde entidad
    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId(), // ⭐ Ya es Long, no necesita conversión
                entity.getName() != null ? entity.getName() : "Producto sin nombre",
                entity.getPrice() != null ? entity.getPrice() : 0.0,
                entity.getStock() != null ? entity.getStock() : 0,
                entity.getCreatedAt());
    }

    // Convertir a entidad
    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (id != null && id > 0)
            entity.setId(id); // ⭐ Ya es Long
        entity.setName(name);
        entity.setPrice(price);
        entity.setStock(stock);
        entity.setCreatedAt(createdAt);
        return entity;
    }

    // Convertir a DTO de respuesta
    public ProductResponseDto toResponseDto() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.id = id;
        dto.name = name;
        dto.price = price;
        dto.stock = stock;
        dto.createdAt = createdAt; 
        return dto;
    }

    // Actualización completa
    public void update(UpdateProductDto dto) {
        if (dto.name != null)
            this.name = dto.name;
        if (dto.price != null)
            this.price = dto.price;
        if (dto.stock != null)
            this.stock = dto.stock;
    }

    // Actualización parcial
    public void partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null && !dto.name.isBlank())
            this.name = dto.name;
        if (dto.price != null)
            this.price = dto.price;
        if (dto.stock != null)
            this.stock = dto.stock;
    }

    // Getters
    public Long getId() { // ⭐ Cambiar retorno a Long
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}