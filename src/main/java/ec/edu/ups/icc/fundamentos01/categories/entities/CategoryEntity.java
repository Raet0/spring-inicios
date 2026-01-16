// src/main/java/ec/edu/ups/icc/fundamentos01/categories/entities/CategoryEntity.java
package ec.edu.ups.icc.fundamentos01.categories.entities;

import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseModel {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    // Relación 1:N - Una categoría tiene muchos productos
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<ProductEntity> products = new ArrayList<>();

    // Constructores
    public CategoryEntity() {
        super();
    }

    public CategoryEntity(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    // Getters y Setters
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

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    // Métodos helper para mantener sincronización bidireccional
    public void addProduct(ProductEntity product) {
        products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.setCategory(null);
    }
}