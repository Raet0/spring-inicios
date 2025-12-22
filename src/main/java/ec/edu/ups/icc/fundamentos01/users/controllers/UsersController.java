package ec.edu.ups.icc.fundamentos01.users.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.User;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;

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
    public ResponseEntity<UserResponseDto> findOne(@PathVariable int id) {

        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .map(user -> ResponseEntity.ok(UserMapper.toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserResponseDto create(@RequestBody CreateUserDto dto) {
        User user = UserMapper.toEntity(currentId++, dto.name, dto.email);
        users.add(user);
        return UserMapper.toResponse(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable int id,
            @RequestBody UpdateUserDto dto) {

        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .map(user -> {
                    user.setName(dto.getName());
                    user.setEmail(dto.getEmail());
                    return ResponseEntity.ok(UserMapper.toResponse(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateUserDto dto) {
        User user = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
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
    // delete

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        boolean exists = users.removeIf(u -> u.getId() == id);
        if (!exists) return new Object() { public String error = "User not found"; };

        return new Object() { public String message = "Deleted successfully"; };
    }
}