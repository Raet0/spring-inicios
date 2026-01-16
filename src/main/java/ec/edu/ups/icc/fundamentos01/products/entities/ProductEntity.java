// src/main/java/ec/edu/ups/icc/fundamentos01/products/entities/ProductEntity.java
package ec.edu.ups.icc.fundamentos01.products.entities;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseModel {  // ⭐ Usar BaseModel

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 500)  // ⭐ NUEVO: campo description
    private String description;

    @Column(nullable = false)
    private Integer stock;

    // ⭐ RELACIÓN MUCHOS A UNO con Category
    @ManyToOne(fetch = FetchType.LAZY, optional = false)  // ⭐ Agregar optional = false
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    // ⭐ RELACIÓN MUCHOS A UNO con User (Owner)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)  // ⭐ Agregar optional = false
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {  // ⭐ NUEVO getter
        return description;
    }

    public void setDescription(String description) {  // ⭐ NUEVO setter
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}