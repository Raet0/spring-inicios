package ec.edu.ups.icc.fundamentos01.users.models;

import java.time.LocalDateTime;

import ec.edu.ups.icc.fundamentos01.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.users.entities.UserEntity;
import jakarta.websocket.MessageHandler.Partial;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    public User(int id, String name, String email, String password) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");

        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido");

        if (password == null || password.length() < 8)
            throw new IllegalArgumentException("Password inválido");

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
    public User( String name, String email, String password) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nombre inválido");

        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email inválido");

        if (password == null || password.length() < 8)
            throw new IllegalArgumentException("Password inválido");

        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ==================== FACTORY METHODS ====================

    /**
     * Crea un User desde un DTO de creación
     * 
     * @param dto DTO con datos del formulario
     * @return instancia de User para lógica de negocio
     */
    public static User fromDto(CreateUserDto dto) {
        return new User(0, dto.name, dto.email, dto.password);
    }

    /**
     * Crea un User desde una entidad persistente
     * 
     * @param entity Entidad recuperada de la BD
     * @return instancia de User para lógica de negocio
     */
    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId().intValue(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword());
    }

    // ==================== CONVERSION METHODS ====================

    /**
     * Convierte este User a una entidad persistente
     * 
     * @return UserEntity lista para guardar en BD
     */
    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        if (this.id > 0) {
            entity.setId((long) this.id);
        }
        entity.setName(this.name);
        entity.setEmail(this.email);
        entity.setPassword(this.password);
        return entity;
    }

    /**
     * Convierte este User a un DTO de respuesta
     * 
     * @return DTO sin información sensible
     */
    public UserResponseDto toResponseDto() {
        UserResponseDto dto = new UserResponseDto(id, name, email, password);

        return dto;
    }

    /**
     * Actualiza los campos permitidos de este User según el DTO
     * 
     * @return DTO sin información sensible
     */
    public User update(UpdateUserDto dto) {
        this.name = dto.name;
        this.email = dto.email;
        this.password = dto.password;
        return this;
    }

    /**
     * Actualiza parcialmente los campos permitidos de este User según el DTO
     * 
     * @return DTO sin información sensible
     */
    public User partialUpdate(PartialUpdateUserDto dto) {
        this.name = dto.name;
        this.email = dto.email;
        this.password = dto.password;
        return this;
    }

}
