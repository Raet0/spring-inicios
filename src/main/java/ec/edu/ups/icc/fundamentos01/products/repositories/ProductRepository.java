package ec.edu.ups.icc.fundamentos01.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByOwnerId(Long userId);

    List<ProductEntity> findByCategoryId(Long categoryId);  // ⭐ AGREGAR ESTE

    List<ProductEntity> findByCategoriesName(String categoryName);

    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findByOwnerName(String name);
    
    // List<ProductEntity> findByCategoryName(String name);
    // List<ProductEntity> findByCategoryIdAndPriceGreaterThan(Long category_id, Double price);

    @Query("SELECT p FROM ProductEntity p" +
        "WHERE SIZE(p.categories) >- categoryCount " +
        "AND :categoryCount = " +
        "(SELECT COUNT(c) FROM p.categories c WHERE c.id IN :categoryIds)")
    List<ProductEntity> findByAllCategories(@Param("categoryIds") List<Long> categoryIds,
                                            @Param("categoryCount") long categoryCount);
}