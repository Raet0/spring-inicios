package ec.edu.ups.icc.fundamentos01.products.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseModel {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

   // @Column(nullable = false)
   // private int id;

    // Getters y setters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    //public int getId() { return id; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
   // public void setId(int id) { this.id = id; }

}
