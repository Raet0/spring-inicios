// src/main/java/ec/edu/ups/icc/fundamentos01/products/services/ProductServiceImpl.java
package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        // // UserEntity owner = userRepository.findById(dto.userId)
        //         .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.userId));

        // 1. validar user
        UserEntity owner = userRepo.findById(dto.userId)
            .orElseThrow() -> new NotFoundException(dto.categoryIds);

        CategoryEntity category = categoryRepository.findById(dto.categoryId)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + dto.categoryId));
        // 2. validar y obtener categorias
        Set<CategoryEntity> categories = validateAndGetCategories(dto.categoryIds);

        // 3. crear dominio
        Product product = Product.fromDto(dto);

        // 4. crear entidad conn relaciones n:n
        ProductEntity entity = product.toEntity(owner);
        entity.setCategories(categories);

        // 5. persistir
        ProductEntity saved = productRepo.save(entity);
        return toResponseDto(saved);
    }

    @Override
    public ProductResponseDto update(Long id, UpdateProductDto dto){
        ProductEntity existing = productRepo.findById(id);
            .orElseThrow(() -> new NotFoundException("producto no encontrado"));

            // validar nuevas categorias
            Set<CategoryEntity> newCategories = validateAndGetCategories(dto.categoryIds);
            // actualizar usando dominio
            Product product = Product.fromEntity(existing);
            product.update(dto);

            // 3. actualizar usando instancia de entidad
        existing.setDescription(dto.description != null ? dto.description : existing.getDescription());
        existing.setName(dto.name != null ? dto.name : existing.getName());
        existing.setPrice(dto.price != null ? dto.price : existing.getPrice());
        
        // IMPORTANTE: Limpiar categorías existentes y asignar nuevas
        existing.clearCategories();
        existing.setCategories(newCategories);

        ProductEntity saved = productRepo.save(existing);
        return toResponseDto(saved);

    }
    // ============== MÉTODOS HELPER ==============

      /**
     * Convierte ProductEntity a DTO incluyendo categorías (N:N)
     * Usa estructura anidada para mejor semántica
     */
    private ProductResponseDto toResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        
        // Campos básicos
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();
        
        // Crear objeto User anidado
        ProductResponseDto.UserSummaryDto userDto = new ProductResponseDto.UserSummaryDto();
        userDto.id = entity.getOwner().getId();
        userDto.name = entity.getOwner().getName();
        userDto.email = entity.getOwner().getEmail();
        dto.user = userDto;
        
        // Convertir Set<CategoryEntity> a List<CategorySummaryDto>
        dto.categories = entity.getCategories().stream()
                .map(this::toCategorySummary)
                .sorted((c1, c2) -> c1.name.compareTo(c2.name)) // Ordenar por nombre
                .toList();
        
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();
        
        return dto;
    }

    private ProductResponseDto.CategorySummaryDto toCategorySummary(CategoryEntity category) {
        ProductResponseDto.CategorySummaryDto summary = new ProductResponseDto.CategorySummaryDto();
        summary.id = category.getId();
        summary.name = category.getName();
        summary.description = category.getDescription();
        return summary;
    }
}

    //     ProductEntity product = new ProductEntity();
    //     product.setName(dto.name);
    //     product.setPrice(dto.price);
    //     product.setDescription(dto.description);
    //     product.setStock(dto.stock != null ? dto.stock : 0); // ⭐ Esta es la línea correcta
    //     product.setCategory(category);
    //     product.setOwner(owner);

    //     ProductEntity saved = productRepository.save(product); // ⭐ Guarda primero
    //     return toResponseDto(saved); // ⭐ Luego retorna
    // }

    // @Override
    // public List<ProductResponseDto> findAll() {
    //     return productRepository.findAll().stream()
    //             .map(this::toResponseDto)
    //             .toList();
    // }

    // @Override
    // public ProductResponseDto findOne(int id) {
    //     ProductEntity entity = productRepository.findById((long) id)
    //             .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    //     return toResponseDto(entity);
    // }

    // @Override
    // public List<ProductResponseDto> findByUserId(Long userId) {
    //     if (!userRepository.existsById(userId)) {
    //         throw new NotFoundException("Usuario no encontrado con ID: " + userId);
    //     }
    //     return productRepository.findByOwnerId(userId).stream()
    //             .map(this::toResponseDto)
    //             .toList();
    // }

    // @Override
    // public List<ProductResponseDto> findByCategoryId(Long categoryId) {
    //     if (!categoryRepository.existsById(categoryId)) {
    //         throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
    //     }
    //     return productRepository.findByCategoryId(categoryId).stream()
    //             .map(this::toResponseDto)
    //             .toList();
    // }

    // @Override
    // public ProductResponseDto update(int id, UpdateProductDto dto) {
    //     ProductEntity product = productRepository.findById((long) id)
    //             .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

    //     CategoryEntity category = categoryRepository.findById(dto.categoryId)
    //             .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + dto.categoryId));

    //     product.setName(dto.name);
    //     product.setPrice(dto.price);
    //     product.setDescription(dto.description);
    //     product.setStock(dto.stock != null ? dto.stock : product.getStock());
    //     product.setCategory(category);

    //     ProductEntity updated = productRepository.save(product);
    //     return toResponseDto(updated);
    // }
    // @Override
    // public ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto) {
    //     ProductEntity product = productRepository.findById((long) id)
    //             .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

    //     if (dto.name != null && !dto.name.isBlank()) {
    //         product.setName(dto.name);
    //     }
    //     if (dto.price != null) {
    //         product.setPrice(dto.price);
    //     }
    //     if (dto.stock != null) {
    //         product.setStock(dto.stock);
    //     }
    //     if (dto.categoryId != null) {
    //         CategoryEntity category = categoryRepository.findById(dto.categoryId)
    //                 .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
    //         product.setCategory(category);
    //     }

    //     ProductEntity updated = productRepository.save(product);
    //     return toResponseDto(updated);
    // }

    // @Override
    // public void delete(int id) {
    //     ProductEntity product = productRepository.findById((long) id)
    //             .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    //     productRepository.delete(product);
    // }

    // private ProductResponseDto toResponseDto(ProductEntity entity) {
    //     ProductResponseDto dto = new ProductResponseDto();
    //     dto.id = entity.getId();
    //     dto.name = entity.getName();
    //     dto.price = entity.getPrice();
    //     dto.description = entity.getDescription();
    //     dto.stock = entity.getStock();

    //     ProductResponseDto.UserSummaryDto userDto = new ProductResponseDto.UserSummaryDto();
    //     userDto.id = entity.getOwner().getId();
    //     userDto.name = entity.getOwner().getName();
    //     userDto.email = entity.getOwner().getEmail();
    //     dto.user = userDto;

    //     ProductResponseDto.CategoryResponseDto categoryDto = new ProductResponseDto.CategoryResponseDto();
    //     categoryDto.id = entity.getCategory().getId();
    //     categoryDto.name = entity.getCategory().getName();
    //     categoryDto.description = entity.getCategory().getDescription();
    //     dto.category = categoryDto;

    //     dto.createdAt = entity.getCreatedAt();
    //     dto.updatedAt = entity.getUpdatedAt();

    //     return dto;
    // }