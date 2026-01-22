package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.exception.domain.BadRequestException;
import ec.edu.ups.icc.fundamentos01.products.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, 
                            UserRepository userRepository,
                            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // ============== MÉTODOS BÁSICOS EXISTENTES ==============
    // (implementaciones previas del tema 08 y 09)

    // ============== MÉTODOS CON PAGINACIÓN ==============

    @Override
    public Page<ProductResponseDto> findAll(int page, int size, String[] sort) {
        Pageable pageable = createPageable(page, size, sort);
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        
        return productPage.map(this::toResponseDto);
    }

    @Override
    public Slice<ProductResponseDto> findAllSlice(int page, int size, String[] sort) {
        Pageable pageable = createPageable(page, size, sort);
        Slice<ProductEntity> productSlice = productRepository.findAll(pageable);
        
        return productSlice.map(this::toResponseDto);
    }

    @Override
    public Page<ProductResponseDto> findWithFilters(
            String name, Double minPrice, Double maxPrice, Long categoryId,
            int page, int size, String[] sort) {
        
        // Validaciones de filtros (del tema 09)
        validateFilterParameters(minPrice, maxPrice);
        
        // Crear Pageable
        Pageable pageable = createPageable(page, size, sort);
        
        // Consulta con filtros y paginación
        Page<ProductEntity> productPage = productRepository.findWithFilters(
            name, minPrice, maxPrice, categoryId, pageable);
        
        return productPage.map(this::toResponseDto);
    }

    @Override
    public Page<ProductResponseDto> findByUserIdWithFilters(
            Long userId, String name, Double minPrice, Double maxPrice, Long categoryId,
            int page, int size, String[] sort) {
        
        // 1. Validar que el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }
        
        // 2. Validar filtros
        validateFilterParameters(minPrice, maxPrice);
        
        // 3. Crear Pageable
        Pageable pageable = createPageable(page, size, sort);
        
        // 4. Consulta con filtros y paginación
        Page<ProductEntity> productPage = productRepository.findByUserIdWithFilters(
            userId, name, minPrice, maxPrice, categoryId, pageable);
        
        return productPage.map(this::toResponseDto);
    }

    // ============== MÉTODOS HELPER ==============

    private Pageable createPageable(int page, int size, String[] sort) {
        // Validar parámetros
        if (page < 0) {
            throw new BadRequestException("La página debe ser mayor o igual a 0");
        }
        if (size < 1 || size > 100) {
            throw new BadRequestException("El tamaño debe estar entre 1 y 100");
        }
        
        // Crear Sort
        Sort sortObj = createSort(sort);
        
        return PageRequest.of(page, size, sortObj);
    }

    private Sort createSort(String[] sort) {
        if (sort == null || sort.length == 0) {
            return Sort.by("id");
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParam : sort) {
            String[] parts = sortParam.split(",");
            String property = parts[0];
            String direction = parts.length > 1 ? parts[1] : "asc";
            
            // Validar propiedades permitidas para evitar inyección SQL
            if (!isValidSortProperty(property)) {
                throw new BadRequestException("Propiedad de ordenamiento no válida: " + property);
            }
            
            Sort.Order order = "desc".equalsIgnoreCase(direction) 
                ? Sort.Order.desc(property)
                : Sort.Order.asc(property);
            
            orders.add(order);
        }
        
        return Sort.by(orders);
    }

    private boolean isValidSortProperty(String property) {
        // Lista blanca de propiedades permitidas para ordenamiento
        Set<String> allowedProperties = Set.of(
            "id", "name", "price", "createdAt", "updatedAt",
            "owner.name", "owner.email", "category.name"
        );
        return allowedProperties.contains(property);
    }

    private void validateFilterParameters(Double minPrice, Double maxPrice) {
        if (minPrice != null && minPrice < 0) {
            throw new BadRequestException("El precio mínimo no puede ser negativo");
        }
        
        if (maxPrice != null && maxPrice < 0) {
            throw new BadRequestException("El precio máximo no puede ser negativo");
        }
        
        if (minPrice != null && maxPrice != null && maxPrice < minPrice) {
            throw new BadRequestException("El precio máximo debe ser mayor o igual al precio mínimo");
        }
    }

    private ProductResponseDto toResponseDto(ProductEntity product) {
        ProductResponseDto dto = new ProductResponseDto();
        
        dto.id = product.getId();
        dto.name = product.getName();
        dto.price = product.getPrice();
        dto.description = product.getDescription();
        dto.createdAt = product.getCreatedAt();
        dto.updatedAt = product.getUpdatedAt();
        
        // Información del usuario (owner)
        dto.user = new ProductResponseDto.UserSummaryDto();
        dto.user.id = product.getOwner().getId();
        dto.user.name = product.getOwner().getName();
        dto.user.email = product.getOwner().getEmail();
        
        // Información de la categoría
        dto.category = new ProductResponseDto.CategoryResponseDto();
        dto.category.id = product.getCategory().getId();
        dto.category.name = product.getCategory().getName();
        dto.category.description = product.getCategory().getDescription();
        
        return dto;
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public List<ProductResponseDto> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ProductResponseDto findOne(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    @Override
    public List<ProductResponseDto> findByUserId(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
    }

    @Override
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCategoryId'");
    }

    @Override
    public ProductResponseDto update(int id, UpdateProductDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partialUpdate'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Page<ProductResponseDto> findAllPaginado(int page, int size, String[] sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllPaginado'");
    }

}