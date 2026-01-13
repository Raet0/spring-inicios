package ec.edu.ups.icc.fundamentos01.products.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseModel {

        @Column(nullable = false, length = 150)
        private String name;

        @Column(nullable = false)
        private Double price;

        @Column(length = 500)
        private String description;

        // ================== RELACIONES 1:N ==================

        /**
         * Relación Many-to-One con User
         * Muchos productos pertenecen a un usuario (owner/creator)
         */
        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private UserEntity owner;

        /**
         * Relación Many-to-One con Category
         * Muchos productos pertenecen a una categoría
         */
        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id", nullable = false)
        private CategoryEntity category;

        // Constructores
        public ProductEntity() {
        }

        // Getters y setter....

        // Getters y setters
        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
        // public int getId() { return id; }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public UserEntity getOwner() {
            return owner;
        }

        public void setOwner(UserEntity owner) {
            this.owner = owner;
        }

        public CategoryEntity getCategory() {
            return category;
        }

        public void setCategory(CategoryEntity category) {
            this.category = category;
        }

        
        // public void setId(int id) { this.id = id; }

}