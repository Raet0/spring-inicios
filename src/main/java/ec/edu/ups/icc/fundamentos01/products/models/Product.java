package ec.edu.ups.icc.fundamentos01.products.models;

import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.entities.ProductEntity;

public class Product {

    private int id;

    private String name;

    private String description;

    private double price;

    private int stock;

    /// Constructores 

    public Product(int id, String name, String description, double price, int stock) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");

        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Descripción inválida");

        if (price <= 0)
            throw new IllegalArgumentException("Precio inválido");

        if (stock < 0)
            throw new IllegalArgumentException("Stock inválido");

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, String description, double price, int stock) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");

        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Descripción inválida");

        if (price <= 0)
            throw new IllegalArgumentException("Precio inválido");

        if (stock < 0)
            throw new IllegalArgumentException("Stock inválido");

        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // ==================== FACTORY METHODS ====================

    /**
     * Crea un Product desde una entidad persistente
     * 
     * @param entity Entidad recu,
     *               entity.getStock()perada de la BD
     * @return instancia de Product para lógica de negocio
     */
    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId().intValue(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(), entity.getStock());
    }

    // ==================== CONVERSION METHODS ====================

    /**
     * Convierte este Product a una entidad persistente
     * 
     * @return ProductEntity lista para guardar en BD
     */
    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0) {
            entity.setId((long) this.id);
        }
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        return entity;
    }

    /**
     * Convierte este Product a un DTO de respuesta
     * 
     * @return DTO sin información sensible
     */
    public ProductResponseDto toResponseDto() {
        ProductResponseDto dto = new ProductResponseDto(id, name, description, price, stock);
        return dto;
    }

    /**
     * Actualiza los campos permitidos de este Product según el DTO
     * 
     * @param dto DTO con nuevos valores
     * @return este mismo Product con valores actualizados
     */
    public Product update(UpdateProductDto dto) {
        if (dto.name == null || dto.name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");

        if (dto.description == null || dto.description.isBlank())
            throw new IllegalArgumentException("Descripción inválida");

        if (dto.price <= 0)
            throw new IllegalArgumentException("Precio inválido");

        if (dto.stock < 0)
            throw new IllegalArgumentException("Stock inválido");

        this.name = dto.name;
        this.description = dto.description;
        this.price = dto.price;
        this.stock = dto.stock;
        return this;
    }

    /**
     * Actualiza parcialmente los campos permitidos de este Product según el DTO
     * 
     * @param dto DTO con valores a actualizar (puede ser nulo alguno)
     * @return este mismo Product con valores parcialmente actualizados
     */
    public Product partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null) {
            if (dto.name.isBlank())
                throw new IllegalArgumentException("Nombre inválido");
            this.name = dto.name;
        }
        if (dto.description != null) {
            if (dto.description.isBlank())
                throw new IllegalArgumentException("Descripción inválida");
            this.description = dto.description;
        }
        if (dto.price != null) {
            if (dto.price <= 0)
                throw new IllegalArgumentException("Precio inválido");
            this.price = dto.price;
        }
        if (dto.stock != null) {
            if (dto.stock < 0)
                throw new IllegalArgumentException("Stock inválido");
            this.stock = dto.stock;
        }
        return this;
    }

}
