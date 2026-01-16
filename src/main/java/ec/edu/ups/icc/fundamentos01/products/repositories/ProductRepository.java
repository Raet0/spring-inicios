package ec.edu.ups.icc.fundamentos01.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByOwnerId(Long userId);

    List<ProductEntity> findByOwnerName(String name);
    
    List<ProductEntity> findByCategoryName(String name);
    
    List<ProductEntity> findByCategoryId(Long categoryId);  // ⭐ AGREGAR ESTE
    
    List<ProductEntity> findByCategoryIdAndPriceGreaterThan(Long category_id, Double price);
}