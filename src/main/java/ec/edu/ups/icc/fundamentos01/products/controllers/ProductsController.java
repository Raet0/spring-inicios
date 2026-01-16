package ec.edu.ups.icc.fundamentos01.products.controllers;

import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import ec.edu.ups.icc.fundamentos01.products.services.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Controlador para operaciones REST de productos
 * Gestiona los endpoints relacionados con productos
 */
@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }

    // ==================== CRUD BÁSICO ====================

    /**
     * GET ALL - Obtiene todos los productos
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * GET ONE - Obtiene un producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.findOne(id.intValue()));
    }

    /**
     * POST - Crea un nuevo producto con relaciones
     */
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody CreateProductDto dto) {
        ProductResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT - Actualiza un producto completo
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductDto dto
    ) {
        return ResponseEntity.ok(service.update(id.intValue(), dto));
    }

    /**
     * PATCH - Actualización parcial de un producto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> partialUpdate(
            @PathVariable Long id,
            @Valid @RequestBody PartialUpdateProductDto dto
    ) {
        return ResponseEntity.ok(service.partialUpdate(id.intValue(), dto));
    }

    /**
     * DELETE - Elimina un producto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id.intValue());
        return ResponseEntity.noContent().build();
    }

    // ==================== CONSULTAS RELACIONALES ====================

    /**
     * GET - Encuentra todos los productos de un usuario específico
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponseDto>> findByUserId(@PathVariable Long userId) {
        ProductServiceImpl serviceImpl = (ProductServiceImpl) service;
        return ResponseEntity.ok(serviceImpl.findByUserId(userId));
    }

    /**
     * GET - Encuentra todos los productos de una categoría específica
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> findByCategoryId(@PathVariable Long categoryId) {
        ProductServiceImpl serviceImpl = (ProductServiceImpl) service;
        return ResponseEntity.ok(serviceImpl.findByCategoryId(categoryId));
    }

    /**
     * GET - Encuentra productos por nombre de usuario
     */
    @GetMapping("/owner/{ownerName}")
    public ResponseEntity<List<ProductResponseDto>> findByOwnerName(@PathVariable String ownerName) {
        ProductServiceImpl serviceImpl = (ProductServiceImpl) service;
        return ResponseEntity.ok(serviceImpl.findByOwnerName(ownerName));
    }

    /**
     * GET - Encuentra productos por nombre de categoría
     */
    @GetMapping("/category-name/{categoryName}")
    public ResponseEntity<List<ProductResponseDto>> findByCategoryName(@PathVariable String categoryName) {
        ProductServiceImpl serviceImpl = (ProductServiceImpl) service;
        return ResponseEntity.ok(serviceImpl.findByCategoryName(categoryName));
    }

    /**
     * GET - Cuenta productos por categoría
     */
    @GetMapping("/category/{categoryId}/count")
    public ResponseEntity<Map<String, Long>> countProductsByCategory(@PathVariable Long categoryId) {
        ProductServiceImpl serviceImpl = (ProductServiceImpl) service;
        Long count = serviceImpl.countProductsByCategory(categoryId);
        return ResponseEntity.ok(Map.of("count", count, "categoryId", categoryId));
    }

    // ==================== VALIDACIONES ====================

    /**
     * POST - Valida si un nombre de producto es único
     */
    @PostMapping("/validate-name")
    public ResponseEntity<Map<String, Boolean>> validateName(@RequestBody ValidateProductDto dto) {
        boolean valid = service.validateName(dto.id, dto.name);
        return ResponseEntity.ok(Map.of("valid", valid));
    }
}