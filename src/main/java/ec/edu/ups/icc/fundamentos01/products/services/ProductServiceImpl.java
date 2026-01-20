package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        UserEntity owner = userRepository.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.userId));

        Set<CategoryEntity> categories = validateAndGetCategories(dto.categoryIds);

        ProductEntity product = new ProductEntity();
        product.setName(dto.name);
        product.setPrice(dto.price);
        product.setDescription(dto.description);
        product.setStock(dto.stock != null ? dto.stock : 0);
        product.setOwner(owner);
        product.setCategories(categories);

        ProductEntity saved = productRepository.save(product);
        return toResponseDto(saved);
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findOne(int id) {
        ProductEntity entity = productRepository.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
        return toResponseDto(entity);
    }

    @Override
    public List<ProductResponseDto> findByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }
        return productRepository.findByOwnerId(userId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
        }
        return productRepository.findByCategoriesId(categoryId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto update(int id, UpdateProductDto dto) {
        ProductEntity product = productRepository.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        Set<CategoryEntity> newCategories = validateAndGetCategories(dto.categoryIds);

        if (dto.name != null) {
            product.setName(dto.name);
        }
        if (dto.price != null) {
            product.setPrice(dto.price);
        }
        if (dto.description != null) {
            product.setDescription(dto.description);
        }
        if (dto.stock != null) {
            product.setStock(dto.stock);
        }

        product.clearCategories();
        product.setCategories(newCategories);

        ProductEntity saved = productRepository.save(product);
        return toResponseDto(saved);
    }

    @Override
    public ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto) {
        ProductEntity product = productRepository.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        if (dto.name != null && !dto.name.isBlank()) {
            product.setName(dto.name);
        }
        if (dto.price != null) {
            product.setPrice(dto.price);
        }
        if (dto.stock != null) {
            product.setStock(dto.stock);
        }
        if (dto.categoryIds != null && !dto.categoryIds.isEmpty()) {
            Set<CategoryEntity> categories = validateAndGetCategories(dto.categoryIds);
            product.clearCategories();
            product.setCategories(categories);
        }

        ProductEntity updated = productRepository.save(product);
        return toResponseDto(updated);
    }

    @Override
    public void delete(int id) {
        ProductEntity product = productRepository.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
        productRepository.delete(product);
    }

    private Set<CategoryEntity> validateAndGetCategories(Set<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una categoría");
        }

        Set<CategoryEntity> categories = new HashSet<>();
        for (Long categoryId : categoryIds) {
            CategoryEntity category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + categoryId));
            categories.add(category);
        }
        return categories;
    }

    private ProductResponseDto toResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();
        dto.stock = entity.getStock();

        ProductResponseDto.UserSummaryDto userDto = new ProductResponseDto.UserSummaryDto();
        userDto.id = entity.getOwner().getId();
        userDto.name = entity.getOwner().getName();
        userDto.email = entity.getOwner().getEmail();
        dto.user = userDto;

        dto.categories = entity.getCategories().stream()
                .map(this::toCategorySummary)
                .sorted((c1, c2) -> c1.name.compareTo(c2.name))
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
