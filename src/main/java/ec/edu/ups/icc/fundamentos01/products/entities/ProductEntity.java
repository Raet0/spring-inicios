// src/main/java/ec/edu/ups/icc/fundamentos01/products/entities/ProductEntity.java
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

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)  
    // @JoinColumn(name = "category_id", nullable = false)
    // private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)  
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories = new HashSet<>();

    // new method
    public Set<CategoryEntity> getCategories(){
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories){
        this.categories = categories;
    }


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

    // public CategoryEntity getCategory() {
    //     return category;
    // }

    // public void setCategory(CategoryEntity category) {
    //     this.category = category;
    // }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    // new method
    public void removeCategory(CategoryEntity entity){
        this.categories.remove(entity);

    }

    public void addCategory(CategoryEntity entity){
        this.categories.add(entity);
    }

    public void clearCategory(CategoryEntity entity){
        this.categories.clear();
    }
}