package ec.edu.ups.icc.fundamentos01.categories.services;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.UpdateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return categoryRepo.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        return categoryRepo.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + id));
    }

    @Override
    public CategoryResponseDto create(CreateCategoryDto dto) {
        
        // Validar que no exista una categoría con ese nombre
        if (categoryRepo.existsByName(dto.name)) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + dto.name);
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.name);
        entity.setDescription(dto.description);

        CategoryEntity saved = categoryRepo.save(entity);
        return toResponseDto(saved);
    }

    @Override
    public CategoryResponseDto update(Long id, UpdateCategoryDto dto) {
        
        CategoryEntity existing = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + id));

        // Validar que no exista otra categoría con ese nombre
        if (!existing.getName().equals(dto.name) && categoryRepo.existsByName(dto.name)) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + dto.name);
        }

        existing.setName(dto.name);
        existing.setDescription(dto.description);

        CategoryEntity updated = categoryRepo.save(existing);
        return toResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        CategoryEntity category = categoryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " + id));
        categoryRepo.delete(category);
    }


    private CategoryResponseDto toResponseDto(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.description = entity.getDescription();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();
        return dto;
    }
}
