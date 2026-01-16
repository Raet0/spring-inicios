package ec.edu.ups.icc.fundamentos01.categories.category;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;


@Repository
public interface RepositoryCategory extends JpaRepository<CategoryEntity, Long> {

    boolean existsByName(String name);
    
    Optional<CategoryEntity> findByName(String name);
}
