package ec.edu.ups.icc.fundamentos01.products.entities;

public class ProductEntitie {

    private int id;
    private String name;
    private int cant;

    public ProductEntitie(int id, String name, int cant) {
        this.id = id;
        this.name = name;
        this.cant = cant;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCant() {
        return cant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
