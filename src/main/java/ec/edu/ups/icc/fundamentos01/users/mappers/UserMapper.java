package ec.edu.ups.icc.fundamentos01.users.mappers;

import ec.edu.ups.icc.fundamentos01.users.entities.User;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

/**
 * Mapper para conversión entre User/UserEntity y UserResponseDto
 * Nota: Preferir usar servicios con estructura adecuada
 */
public class UserMapper {

    public static User toEntity(int id, String name, String email) {
        return new User(id, name, email, "secret");
    }

    public static UserResponseDto toResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = (long) user.getId();
        dto.name = user.getName();
        dto.email = user.getEmail();
        return dto;
    }

    public static UserResponseDto toResponse(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.email = entity.getEmail();
        return dto;
    }
}
