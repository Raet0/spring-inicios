package ec.edu.ups.icc.fundamentos01.categories.entities;

import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseModel {
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    // Relación N:N - Una categoría tiene muchos productos, un producto tiene muchas categorías
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<ProductEntity> products = new HashSet<>();

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

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products != null ? products : new HashSet<>();
    }

    // Métodos helper para mantener sincronización bidireccional
    public void addProduct(ProductEntity product) {
        this.products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(ProductEntity product) {
        this.products.remove(product);
        product.getCategories().remove(this);
    }

}