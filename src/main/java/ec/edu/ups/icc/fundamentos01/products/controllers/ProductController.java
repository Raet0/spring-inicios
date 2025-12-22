package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntitie;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<ProductEntitie> products = new ArrayList<>();
    private int currentId = 1;

    // GET ALL
    @GetMapping
    public List<ProductResponseDto> findAll() {
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Object findOne(@PathVariable int id) {

        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(product -> (Object) ProductMapper.toResponse(product))
                .orElseGet(() -> new Object() {
                    public String error = "Product not found";
                });
    }

    // CREATE
    @PostMapping
    public ProductResponseDto create(@RequestBody CreateProductDto dto) {

        ProductEntitie product =
                ProductMapper.toEntity(currentId++, dto.name, dto.cant);

        products.add(product);

        return ProductMapper.toResponse(product);
    }

    // PUT (UPDATE COMPLETE)
    @PutMapping("/{id}")
    public Object update(
            @PathVariable int id,
            @RequestBody UpdateProductDto dto) {

        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(product -> {
                    product.setName(dto.name);
                    product.setCant(dto.cant);
                    return (Object) ProductMapper.toResponse(product);
                })
                .orElseGet(() -> new Object() {
                    public String error = "Product not found";
                });
    }

    // PATCH (UPDATE PARTIAL)
    @PatchMapping("/{id}")
    public Object partialUpdate(
            @PathVariable int id,
            @RequestBody PartialUpdateProductDto dto) {

        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(product -> {

                    if (dto.name != null)
                        product.setName(dto.name);

                    if (dto.cant != null)
                        product.setCant(dto.cant);

                    return (Object) ProductMapper.toResponse(product);
                })
                .orElseGet(() -> new Object() {
                    public String error = "Product not found";
                });
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {

        boolean exists = products.removeIf(p -> p.getId() == id);

        if (!exists) {
            return new Object() {
                public String error = "Product not found";
            };
        }

        return new Object() {
            public String message = "Deleted successfully";
        };
    }
}
