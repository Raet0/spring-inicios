package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Servicio para operaciones de Product
 * Orquesta la creación, actualización y consulta de productos con sus relaciones
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public ProductServiceImpl(
            ProductRepository productRepo,
            UserRepository userRepo,
            CategoryRepository categoryRepo
    ) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepo.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findOne(int id) {
        return productRepo.findById((long) id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        
        // 1. VALIDAR EXISTENCIA DE RELACIONES
        UserEntity owner = userRepo.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.userId));

        Set<CategoryEntity> categories = validateAndGetCategories(dto.categoryIds);

        // 2. REGLA: nombre único
        if (productRepo.existsByName(dto.name)) {
            throw new ConflictException("El nombre del producto ya está registrado: " + dto.name);
        }

        // 3. CREAR MODELO DE DOMINIO
        Product product = Product.fromDto(dto);

        // 4. CONVERTIR A ENTIDAD CON RELACIONES
        ProductEntity entity = product.toEntity(owner, categories);

        // 5. PERSISTIR
        ProductEntity saved = productRepo.save(entity);

        // 6. CONVERTIR A DTO DE RESPUESTA
        return toResponseDto(saved);
    }

    @Override
    public ProductResponseDto update(int id, UpdateProductDto dto) {
        
        // 1. BUSCAR PRODUCTO EXISTENTE
        ProductEntity existing = productRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        // 2. VALIDAR NUEVAS CATEGORÍAS
        Set<CategoryEntity> newCategories = validateAndGetCategories(dto.categoryIds);

        // 3. ACTUALIZAR CAMPOS
        existing.setName(dto.name);
        existing.setPrice(dto.price);
        existing.setStock(dto.stock);
        existing.setDescription(dto.description);
        
        // IMPORTANTE: Limpiar categorías existentes y asignar nuevas
        existing.clearCategories();
        existing.setCategories(newCategories);

        // 4. PERSISTIR Y RESPONDER
        ProductEntity saved = productRepo.save(existing);
        return toResponseDto(saved);
    }

    @Override
    public ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto) {
        
        ProductEntity existing = productRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        // Actualizar solo los campos no nulos
        if (dto.name != null && !dto.name.isBlank()) {
            existing.setName(dto.name);
        }
        if (dto.price != null) {
            existing.setPrice(dto.price);
        }
        if (dto.stock != null) {
            existing.setStock(dto.stock);
        }
        if (dto.description != null && !dto.description.isBlank()) {
            existing.setDescription(dto.description);
        }

        ProductEntity saved = productRepo.save(existing);
        return toResponseDto(saved);
    }

    @Override
    public void delete(int id) {
        ProductEntity product = productRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
        productRepo.delete(product);
    }

    @Override
    public boolean validateName(Integer id, String name) {
        productRepo.findByName(name)
                .ifPresent(existing -> {
                    if (id == null || !existing.getId().equals(id.longValue())) {
                        throw new ConflictException("Ya existe un producto con el nombre: " + name);
                    }
                });
        return true;
    }

    /**
     * Encontrar productos por usuario
     */
    public List<ProductResponseDto> findByUserId(Long userId) {
        
        // Validar que el usuario existe
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }

        return productRepo.findByOwnerId(userId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Encontrar productos por categoría
     */
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {
        
        // Validar que la categoría existe
        if (!categoryRepo.existsById(categoryId)) {
            throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
        }

        return productRepo.findByCategoriesId(categoryId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Encontrar productos por nombre de usuario
     */
    public List<ProductResponseDto> findByOwnerName(String ownerName) {
        return productRepo.findByOwnerName(ownerName)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Encontrar productos por nombre de categoría
     */
    public List<ProductResponseDto> findByCategoryName(String categoryName) {
        return productRepo.findByCategoriesName(categoryName)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    /**
     * Contar productos por categoría
     */
    public Long countProductsByCategory(Long categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
        }
        
        return (long) productRepo.findByCategoriesId(categoryId).size();
    }

    // ============== MÉTODOS HELPER ==============

    /**
     * Valida que todas las categorías existan y las retorna en un Set
     */
    private Set<CategoryEntity> validateAndGetCategories(Set<Long> categoryIds) {
        Set<CategoryEntity> categories = new HashSet<>();
        
        for (Long categoryId : categoryIds) {
            CategoryEntity category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + categoryId));
            categories.add(category);
        }
        
        return categories;
    }

    /**
     * Convierte ProductEntity a ProductResponseDto
     * Usa estructura anidada para mejor organización semántica
     */
    private ProductResponseDto toResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        
        // Campos básicos del producto
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.stock = entity.getStock();
        dto.description = entity.getDescription();
        
        // Crear objeto User anidado (se carga LAZY)
        ProductResponseDto.UserSummaryDto userDto = new ProductResponseDto.UserSummaryDto();
        userDto.id = entity.getOwner().getId();
        userDto.name = entity.getOwner().getName();
        userDto.email = entity.getOwner().getEmail();
        dto.user = userDto;
        
        // Convertir Set<CategoryEntity> a List<CategoryResponseDto>
        dto.categories = entity.getCategories().stream()
                .map(this::toCategoryResponseDto)
                .sorted((c1, c2) -> c1.name.compareTo(c2.name)) // Ordenar por nombre
                .toList();
        
        // Auditoría
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();
        
        return dto;
    }

    /**
     * Convierte CategoryEntity a CategoryResponseDto
     */
    private ProductResponseDto.CategoryResponseDto toCategoryResponseDto(CategoryEntity category) {
        ProductResponseDto.CategoryResponseDto summary = new ProductResponseDto.CategoryResponseDto();
        summary.id = category.getId();
        summary.name = category.getName();
        summary.description = category.getDescription();
        return summary;
    }
}
