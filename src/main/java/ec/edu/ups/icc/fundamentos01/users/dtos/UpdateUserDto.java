package ec.edu.ups.icc.fundamentos01.users.dtos;

public class UpdateUserDto {
  public String name;
  public String email;

  public String getEmail(){
    return email;
  }
  public String getName(){
    return name;
  }
  public void setName(String name){
    this.name = name;
  }
  public void setEmail(String email){
    this.email = email;
  }
}
