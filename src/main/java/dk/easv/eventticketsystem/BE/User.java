package dk.easv.eventticketsystem.BE;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;

    //constructor
    public User(int userId, String firstName, String lastName, String username, String password, Role role){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //getters and setters
    public int getUserId(){
        return userId;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getUsername(){
        return username;
    }

    public Role getRole(){
        return role;
    }

    public String getPassword(){
        return password;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }
}
