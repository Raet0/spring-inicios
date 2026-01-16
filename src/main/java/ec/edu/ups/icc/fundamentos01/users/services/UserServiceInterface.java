package ec.edu.ups.icc.fundamentos01.users.services;

import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import java.util.List;


public interface UserServiceInterface {

    List<UserResponseDto> findAll();
    
    UserResponseDto findById(Long id);
    
    UserResponseDto create(CreateUserDto dto);
    
    UserResponseDto update(Long id, UpdateUserDto dto);
    
    UserResponseDto partialUpdate(Long id, PartialUpdateUserDto dto);
    
    void delete(Long id);
}
