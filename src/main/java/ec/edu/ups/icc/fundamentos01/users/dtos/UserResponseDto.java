package ec.edu.ups.icc.fundamentos01.users.dtos;

public class UserResponseDto {
    public int id;
    public String name;
    public String email;
    public String password;

    
    public UserResponseDto(int id, String name, String email, String password   ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

  
}
