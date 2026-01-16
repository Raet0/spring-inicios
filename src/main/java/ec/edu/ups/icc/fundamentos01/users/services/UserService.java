package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;

public interface UserService {

    List<UserResponseDto> findAll();

    Object findOne(int id);

    UserResponseDto create(CreateUserDto dto);

    Object update(int id, UpdateUserDto dto);

    Object partialUpdate(int id, PartialUpdateUserDto dto);

    void delete(int id);

}