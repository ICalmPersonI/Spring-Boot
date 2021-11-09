package engine.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    @Email
    @Pattern(regexp=".+@.+\\..+")
    private String email;
    @Column
    @Pattern(regexp="^[^\\s]+$")
    @Length(min = 5)
    private String password;

    User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    User() { }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
