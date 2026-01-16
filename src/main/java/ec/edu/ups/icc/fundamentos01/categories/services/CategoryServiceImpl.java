// src/main/java/ec/edu/ups/icc/fundamentos01/categories/services/CategoryServiceImpl.java
package ec.edu.ups.icc.fundamentos01.categories.services;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.UpdateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.products.exception.domain.BadRequestException;
import ec.edu.ups.icc.fundamentos01.products.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.products.exception.domain.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponseDto create(CreateCategoryDto dto) {
        // Validar que no exista
        if (categoryRepository.findByName(dto.name).isPresent()) {
            throw new ConflictException("La categoría ya existe");
        }

        CategoryEntity category = new CategoryEntity(dto.name, dto.description);
        CategoryEntity saved = categoryRepository.save(category);

        return toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto findById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
        return toResponseDto(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto update(Long id, UpdateCategoryDto dto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        // Validar nombre único si cambió
        if (!category.getName().equals(dto.name)) {
            if (categoryRepository.findByName(dto.name).isPresent()) {
                throw new ConflictException("Ya existe una categoría con ese nombre");
            }
        }

        category.setName(dto.name);
        category.setDescription(dto.description);

        CategoryEntity updated = categoryRepository.save(category);
        return toResponseDto(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        // Validar que no tenga productos asociados
        if (!category.getProducts().isEmpty()) {
            throw new BadRequestException(
                "No se puede eliminar la categoría porque tiene " + 
                category.getProducts().size() + " productos asociados"
            );
        }

        categoryRepository.delete(category);
    }

    private CategoryResponseDto toResponseDto(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.description = entity.getDescription();
        dto.productCount = entity.getProducts().size();
        dto.createdAt = entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null;
        dto.updatedAt = entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null;
        return dto;
    }
}