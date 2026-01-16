package ec.edu.ups.icc.fundamentos01.products.entities;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.exception.domain.BadRequestException;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import java.util.Set;

/**
 * Modelo de dominio Product
 * Encapsula la lógica de negocio, sin conocer JPA
 */
public class Product {

    private Long id;
    private String name;
    private Double price;
    private int stock;
    private String description;

    // ===== Constructores =====

    public Product() {
    }

    public Product(String name, Double price, int stock, String description) {
        this.validateBusinessRules(name, price, stock, description);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    // ===== VALIDACIONES DE NEGOCIO =====

    private void validateBusinessRules(String name, Double price, int stock, String description) {
        if (name == null || name.isBlank() || name.length() < 3) {
            throw new BadRequestException("Nombre inválido: debe tener al menos 3 caracteres");
        }
        if (price == null || price < 0) {
            throw new BadRequestException("Precio inválido: no puede ser negativo");
        }
        if (stock < 0) {
            throw new BadRequestException("Stock inválido: no puede ser negativo");
        }
        if (description != null && description.length() > 500) {
            throw new BadRequestException("La descripción no puede superar 500 caracteres");
        }
    }

    // ==================== FACTORY METHODS ====================

    /**
     * Crea un Product desde un DTO de creación
     */
    public static Product fromDto(CreateProductDto dto) {
        return new Product(dto.name, dto.price, dto.stock, dto.description);
    }

    /**
     * Crea un Product desde una entidad persistente
     */
    public static Product fromEntity(ProductEntity entity) {
        Product product = new Product(
            entity.getName(),
            entity.getPrice(),
            entity.getStock(),
            entity.getDescription()
        );
        product.id = entity.getId();
        return product;
    }

    // ==================== CONVERSION METHODS ====================

    /**
     * Convierte este Product a una entidad persistente
     * REQUIERE las entidades relacionadas como parámetros
     */
    public ProductEntity toEntity(UserEntity owner, Set<CategoryEntity> categories) {
        ProductEntity entity = new ProductEntity();
        
        if (this.id != null && this.id > 0) {
            entity.setId(this.id);
        }
        
        entity.setName(this.name);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        entity.setDescription(this.description);
        
        // Asignar relaciones
        entity.setOwner(owner);
        entity.setCategories(categories);
        
        return entity;
    }

    /**
     * Actualiza los campos modificables de este Product
     */
    public Product update(UpdateProductDto dto) {
        this.validateBusinessRules(dto.name, dto.price, dto.stock, dto.description);
        this.name = dto.name;
        this.price = dto.price;
        this.stock = dto.stock;
        this.description = dto.description;
        return this;
    }

    /**
     * Actualiza parcialmente los campos de este Product
     */
    public Product partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null) {
            if (dto.name.isBlank() || dto.name.length() < 3) {
                throw new BadRequestException("Nombre inválido: debe tener al menos 3 caracteres");
            }
            this.name = dto.name;
        }
        if (dto.price != null) {
            if (dto.price < 0) {
                throw new BadRequestException("Precio inválido: no puede ser negativo");
            }
            this.price = dto.price;
        }
        if (dto.stock != null) {
            if (dto.stock < 0) {
                throw new BadRequestException("Stock inválido: no puede ser negativo");
            }
            this.stock = dto.stock;
        }
        return this;
    }

    // ===== Getters y Setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
