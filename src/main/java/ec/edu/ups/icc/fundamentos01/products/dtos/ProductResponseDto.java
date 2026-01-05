package ec.edu.ups.icc.fundamentos01.products.dtos;

public class ProductResponseDto {
    public int id;
    public String name;
    public String description;
    public double price;
    public int stock;

    public ProductResponseDto() {
    }

    public ProductResponseDto(int id, String name, String description, double price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}

