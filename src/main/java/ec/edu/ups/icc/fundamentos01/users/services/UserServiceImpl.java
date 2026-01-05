package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.User;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service

public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Actualizar código de los métodos para usar userRepo
    //
    public List<UserResponseDto> findAll() {

        // 1. El repositorio devuelve entidades JPA (UserEntity)
        return userRepo.findAll()
                .stream()

                // 2. Cada UserEntity se transforma en un modelo de dominio User
                // Aquí se desacopla la capa de persistencia de la lógica de negocio
                .map(User::fromEntity)

                // 3. El modelo de dominio se convierte en DTO de respuesta
                // Solo se exponen los campos permitidos por la API
                .map(UserMapper::toResponse)

                // 4. Se recopila el resultado final como una lista de DTOs
                .toList();
    }

    @Override
    public UserResponseDto findOne(int id) {
        return userRepo.findById((long) id)
                .map(User::fromEntity)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        if (userRepo.findByEmail(dto.email).isPresent()) {
            throw new ConflictException("El email ya está registrado");
        }

        return Optional.of(dto)
                .map(UserMapper::fromCreateDto)
                .map(User::toEntity)
                .map(userRepo::save)
                .map(User::fromEntity)
                .map(UserMapper::toResponse)

                .orElseThrow(() -> new BadRequestException("Error al crear el usuario"));
    }

    @Override
    public UserResponseDto update(int id, UpdateUserDto dto) {

        return userRepo.findById((long) id)
                // Entity → Domain
                .map(User::fromEntity)

                // Aplicar cambios permitidos en el dominio
                .map(user -> user.update(dto))

                // Domain → Entity
                .map(User::toEntity)

                // Persistencia
                .map(userRepo::save)

                // Entity → Domain
                .map(User::fromEntity)

                // Domain → DTO
                .map(UserMapper::toResponse)

                // Error controlado si no existe
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
    }

    @Override
    public UserResponseDto partialUpdate(int id, PartialUpdateUserDto dto) {

        return userRepo.findById((long) id)
                // Entity → Domain
                .map(User::fromEntity)

                // Aplicar solo los cambios presentes
                .map(user -> user.partialUpdate(dto))

                // Domain → Entity
                .map(User::toEntity)

                // Persistencia
                .map(userRepo::save)

                // Entity → Domain
                .map(User::fromEntity)

                // Domain → DTO
                .map(UserMapper::toResponse)

                // Error si no existe
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
    }

    @Override
    public void delete(int id) {
        // Verifica existencia y elimina
        userRepo.findById((long) id)
                .ifPresentOrElse(
                        userRepo::delete,
                        () -> {
                            throw new BadRequestException("Usuario no encontrado");
                        });
    }
}
