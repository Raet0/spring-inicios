package ec.edu.ups.icc.fundamentos01.users.controllers;

import ec.edu.ups.icc.fundamentos01.users.entities.User;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.services.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private List<User> users = new ArrayList<>();
    private int currentId = 1;

    @GetMapping
    public List<UserResponseDto> findAll() {
        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }

 @GetMapping("/{id}")
public Object findOne(@PathVariable int id) {

    for (User user : users) {
        if (user.getId() == id) {
            return UserMapper.toResponse(user);
        }
    }

    return new Object() {
        public String error = "User not found";
    };
}


    @PostMapping
    public UserResponseDto create(@RequestBody CreateUserDto dto) {
        User user = UserMapper.toEntity(currentId++, dto.name, dto.email);
        users.add(user);
        return UserMapper.toResponse(user);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable int id, @RequestBody UpdateUserDto dto) {

        User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

        if (user == null)
            return new Object() {
                public String error = "User not found";
            };

        user.setName(dto.name);
        user.setEmail(dto.email);

        return UserMapper.toResponse(user);
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateUserDto dto) {

        User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

        if (user == null)
            return new Object() {
                public String error = "User not found";
            };

        if (dto.name != null)
            user.setName(dto.name);
        if (dto.email != null)
            user.setEmail(dto.email);

        return UserMapper.toResponse(user);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {

        boolean deleted = users.removeIf(u -> u.getId() == id);

        if (!deleted)
            return new Object() {
                public String error = "User not found";
            };

        return new Object() {
            public String message = "Deleted successfully";
        };
    }

    private final UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }

}
