package ec.edu.ups.icc.fundamentos01.products.controllers;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductsController {


    private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }


    // GET ALL
    @GetMapping
    public java.util.List<ProductResponseDto> findAll() {
        return service.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public ProductResponseDto findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    // POST
    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody CreateProductDto dto) {
        return service.create(dto);
    }

    // PUT
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable int id, @Valid @RequestBody UpdateProductDto dto) {
        return service.update(id, dto);
    }

    // PATCH
    @PatchMapping("/{id}")
    public ProductResponseDto partialUpdate(@PathVariable int id, @Valid @RequestBody PartialUpdateProductDto dto) {
        return service.partialUpdate(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable int id) {
        service.delete(id);
        return Map.of("message", "Deleted successfully");
    }

}
            