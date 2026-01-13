package ec.edu.ups.icc.fundamentos01.products.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

    boolean existByName(String name);
    Optional<CategoryEntity> findBy
    
}