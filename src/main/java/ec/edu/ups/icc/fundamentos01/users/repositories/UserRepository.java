package ec.edu.ups.icc.fundamentos01.users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity , Long> {
    Optional<UserEntity> findByEmail(String email);
    
} 