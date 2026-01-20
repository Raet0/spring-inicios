package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.models.User;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ProductRepository productRepository;

    public UserServiceImpl(UserRepository userRepo, ProductRepository productRepository) {
        this.userRepo = userRepo;
        this.productRepository = productRepository;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepo.findAll()
                .stream()
                .map(User::fromEntity)
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDto findOne(int id) {
        return userRepo.findById((long) id)
                .map(User::fromEntity)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        User user = User.fromDto(dto);
        UserEntity entity = user.toEntity();
        UserEntity saved = userRepo.save(entity);
        return UserMapper.toResponse(User.fromEntity(saved));
    }

    @Override
    public UserResponseDto update(int id, UpdateUserDto dto) {
        return userRepo.findById((long) id)
                .map(User::fromEntity)
                .map(user -> user.update(dto))
                .map(User::toEntity)
                .map(userRepo::save)
                .map(User::fromEntity)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public UserResponseDto partialUpdate(int id, PartialUpdateUserDto dto) {
        return userRepo.findById((long) id)
                .map(User::fromEntity)
                .map(user -> user.partialUpdate(dto))
                .map(User::toEntity)
                .map(userRepo::save)
                .map(User::fromEntity)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public void delete(int id) {
        userRepo.findById((long) id)
                .ifPresentOrElse(
                        userRepo::delete,
                        () -> {
                            throw new IllegalStateException("User not found");
                        }
                );
    }

    @Override
    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new IllegalStateException("User not found");
        }
        return productRepository.findByOwnerId(userId).stream()
                .map(this::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductsByUserIdWithFilters(
            Long userId,
            String name,
            Double minPrice,
            Double maxPrice,
            Long categoryId) {
        
        // Validar que el usuario existe
        if (!userRepo.existsById(userId)) {
            throw new IllegalStateException("User not found with ID: " + userId);
        }

        // Consultar productos con filtros directamente en la BD
        List<ProductEntity> products = productRepository.findByOwnerIdWithFilters(
                userId,
                name,
                minPrice,
                maxPrice,
                categoryId
        );

        // Mapear a DTOs
        return products.stream()
                .map(this::toProductResponse)
                .toList();
    }

    private ProductResponseDto toProductResponse(ProductEntity entity) {
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
