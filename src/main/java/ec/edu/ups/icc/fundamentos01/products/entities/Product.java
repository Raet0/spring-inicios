package ec.edu.ups.icc.fundamentos01.products.entities;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.exception.domain.BadRequestException;

public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        // Validaciones de dominio
        if (name == null || name.isBlank() || name.length() < 3) {
            throw new BadRequestException("Nombre inválido: debe tener al menos 3 caracteres");
        }
        if (price < 0) {
            throw new BadRequestException("Precio inválido: no puede ser negativo");
        }
        if (stock < 0) {
            throw new BadRequestException("Stock inválido: no puede ser negativo");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }


    public static Product fromEntity(ProductEntity entity) {
        return new Product(
            entity.getId().intValue(),
            entity.getName(),
            entity.getPrice(),
            entity.getStock()
        );
    }

    public static Product fromCreateDto(CreateProductDto dto) {
        return new Product(0, dto.name, dto.price, dto.stock);
    }

    

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (this.id > 0) {
            entity.setId((long) this.id);
        }
        entity.setName(this.name);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        return entity;
    }

    

    public Product update(UpdateProductDto dto) {
        // Validaciones
        if (dto.name == null || dto.name.isBlank() || dto.name.length() < 3) {
            throw new BadRequestException("Nombre inválido: debe tener al menos 3 caracteres");
        }
        if (dto.price < 0) {
            throw new BadRequestException("Precio inválido: no puede ser negativo");
        }
        if (dto.stock < 0) {
            throw new BadRequestException("Stock inválido: no puede ser negativo");
        }

        this.name = dto.name;
        this.price = dto.price;
        this.stock = dto.stock;
        return this;
    }

    public Product partialUpdate(PartialUpdateProductDto dto) {
        if (dto.name != null) {
            if (dto.name.isBlank() || dto.name.length() < 3) {
                throw new BadRequestException("Nombre inválido: debe tener al menos 3 caracteres");
            }
            this.name = dto.name;
        }
        if (dto.price != null) {
            if (dto.price < 0) throw new BadRequestException("Precio inválido: no puede ser negativo");
            this.price = dto.price;
        }
        if (dto.stock != null) {
            if (dto.stock < 0) throw new BadRequestException("Stock inválido: no puede ser negativo");
            this.stock = dto.stock;
        }
        return this;
    }
}
