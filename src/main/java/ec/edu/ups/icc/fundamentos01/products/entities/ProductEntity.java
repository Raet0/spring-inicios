package ec.edu.ups.icc.fundamentos01.products.entities;

import java.util.HashSet;
import java.util.Set;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseModel {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer stock;

    // ============== RELACIÓN 1:N (se mantiene) ==============
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    // ============== NUEVA RELACIÓN N:N ==============
    /**
     * Relación Many-to-Many con Category
     * Un producto puede tener múltiples categorías
     * Una categoría puede estar en múltiples productos
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_categories",                    // Tabla intermedia
        joinColumns = @JoinColumn(name = "product_id"), // FK hacia products
        inverseJoinColumns = @JoinColumn(name = "category_id") // FK hacia categories
    )
    private Set<CategoryEntity> categories = new HashSet<>();

    // ============== CONSTRUCTORES ==============
    public ProductEntity() {
    }

    // ============== GETTERS Y SETTERS ==============
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories != null ? categories : new HashSet<>();
    }

    // ============== MÉTODOS DE CONVENIENCIA ==============
    /**
     * Agrega una categoría al producto y sincroniza la relación bidireccional
     */
    public void addCategory(CategoryEntity category) {
        this.categories.add(category);
        category.getProducts().add(this);
    }

    /**
     * Remueve una categoría del producto y sincroniza la relación bidireccional
     */
    public void removeCategory(CategoryEntity category) {
        this.categories.remove(category);
        category.getProducts().remove(this);
    }

    /**
     * Limpia todas las categorías y sincroniza las relaciones
     */
    public void clearCategories() {
        for (CategoryEntity category : new HashSet<>(this.categories)) {
            removeCategory(category);
        }
    }

}