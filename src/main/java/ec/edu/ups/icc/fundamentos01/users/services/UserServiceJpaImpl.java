package ec.edu.ups.icc.fundamentos01.users.services;

import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementación de servicio para operaciones de User
 * Maneja la persistencia y lógica de negocio de usuarios
 */
@Service
public class UserServiceJpaImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceJpaImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepo.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto findOne(int id) {
        return userRepo.findById((long) id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        
        // Validar que no exista un usuario con ese email
        if (userRepo.findByEmail(dto.email).isPresent()) {
            throw new ConflictException("Ya existe un usuario con el email: " + dto.email);
        }

        UserEntity entity = new UserEntity();
        entity.setName(dto.name);
        entity.setEmail(dto.email);
        entity.setPassword(dto.password != null ? dto.password : "default_password");

        UserEntity saved = userRepo.save(entity);
        return toResponseDto(saved);
    }

    @Override
    public Object update(int id, UpdateUserDto dto) {
        
        UserEntity existing = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        // Validar que no exista otro usuario con ese email
        if (!existing.getEmail().equals(dto.email) && userRepo.findByEmail(dto.email).isPresent()) {
            throw new ConflictException("Ya existe un usuario con el email: " + dto.email);
        }

        existing.setName(dto.name);
        existing.setEmail(dto.email);

        UserEntity updated = userRepo.save(existing);
        return toResponseDto(updated);
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateUserDto dto) {
        
        UserEntity existing = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        if (dto.name != null && !dto.name.isBlank()) {
            existing.setName(dto.name);
        }

        if (dto.email != null && !dto.email.isBlank()) {
            if (!existing.getEmail().equals(dto.email) && userRepo.findByEmail(dto.email).isPresent()) {
                throw new ConflictException("Ya existe un usuario con el email: " + dto.email);
            }
            existing.setEmail(dto.email);
        }

        UserEntity updated = userRepo.save(existing);
        return toResponseDto(updated);
    }

    @Override
    public Object delete(int id) {
        UserEntity user = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));
        userRepo.delete(user);
        return new Object() { public String message = "Usuario eliminado exitosamente"; };
    }

    // ============== MÉTODOS HELPER ==============

    /**
     * Convierte UserEntity a UserResponseDto
     */
    private UserResponseDto toResponseDto(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.email = entity.getEmail();
        return dto;
    }
}
