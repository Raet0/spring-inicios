package ec.edu.ups.icc.fundamentos01.products.services;

import org.springframework.stereotype.Service;

import java.util.Optional;


import ec.edu.ups.icc.fundamentos01.fundamentos01.exception.domain.BadRequestException;
import ec.edu.ups.icc.fundamentos01.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.models.Product;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductResponseDto> findAll() {

        // 1. El repositorio devuelve entidades JPA (ProductEntity)
        return productRepo.findAll()
                .stream()

                // 2. Cada ProductEntity se transforma en un modelo de dominio Product
                // Aquí se desacopla la capa de persistencia de la lógica de negocio
                .map(Product::fromEntity)

                // 3. El modelo de dominio se convierte en DTO de respuesta
                // Solo se exponen los campos permitidos por la API
                .map(ProductMapper::toResponse)

                // 4. Se recopila el resultado final como una lista de DTOs
                .toList();
    }

    @Override
    public ProductResponseDto findOne(int id) {
        return productRepo.findById((long) id)
                .map(Product::fromEntity)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        try {
            if (productRepo.findByName(dto.name).isPresent()) {
                throw new ConflictException("El nombre ya está registrado");
            }
            return Optional.of(dto)
                    .map(ProductMapper::fromCreateDto)
                    .map(Product::toEntity)
                    .map(productRepo::save)
                    .map(Product::fromEntity)
                    .map(ProductMapper::toResponse)

                    .orElseThrow(() -> new BadRequestException("Error al crear el producto"));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Datos inválidos: " + e.getMessage());
        }
    }

    @Override
    public ProductResponseDto update(int id, UpdateProductDto dto) {
        try {
            return productRepo.findById((long) id)
                    // Entity → Domain
                    .map(Product::fromEntity)

                    // Aplicar cambios permitidos en el dominio
                    .map(product -> product.update(dto))

                    // Domain → Entity
                    .map(Product::toEntity)

                    // Persistencia
                    .map(productRepo::save)

                    // Entity → Domain
                    .map(Product::fromEntity)

                    // Domain → DTO
                    .map(ProductMapper::toResponse)

                    // Error controlado si no existe
                    .orElseThrow(() -> new BadRequestException("Producto no encontrado"));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Datos inválidos: " + e.getMessage());
        }
    }

    @Override
    public ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto) {
        try {
            return productRepo.findById((long) id)
                    // Entity → Domain
                    .map(Product::fromEntity)

                    // Aplicar solo los cambios presentes
                    .map(product -> product.partialUpdate(dto))

                    // Domain → Entity
                    .map(Product::toEntity)

                    // Persistencia
                    .map(productRepo::save)

                    // Entity → Domain
                    .map(Product::fromEntity)

                    // Domain → DTO
                    .map(ProductMapper::toResponse)

                    // Error si no existe
                    .orElseThrow(() -> new BadRequestException("Producto no encontrado"));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Datos inválidos: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        // Verifica existencia y elimina
        productRepo.findById((long) id)
                .ifPresentOrElse(
                        productRepo::delete,
                        () -> {
                            throw new BadRequestException("Producto no encontrado");
                        });
    }
  
}
