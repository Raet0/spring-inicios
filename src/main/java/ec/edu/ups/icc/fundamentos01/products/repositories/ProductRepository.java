package ec.edu.ups.icc.fundamentos01.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de ProductEntity
 * Incluye consultas relacionales con User y Category
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Verifica si existe un producto con ese nombre
     */
    boolean existsByName(String name);

    /**
     * Encuentra un producto por nombre
     */
    Optional<ProductEntity> findByName(String name);

    /**
     * Encuentra todos los productos de un usuario específico
     */
    List<ProductEntity> findByOwnerId(Long userId);

    /**
     * Encuentra todos los productos que tienen una categoría específica
     */
    List<ProductEntity> findByCategoriesId(Long categoryId);

    /**
     * Encuentra productos por nombre de usuario
     * Genera JOIN automáticamente
     */
    List<ProductEntity> findByOwnerName(String ownerName);

    /**
     * Encuentra productos por nombre de categoría
     * Genera JOIN automáticamente
     */
    List<ProductEntity> findByCategoriesName(String categoryName);

    /**
     * Encuentra productos con precio mayor a X de una categoría específica
     */
    List<ProductEntity> findByCategoriesIdAndPriceGreaterThan(Long categoryId, Double price);
} 

