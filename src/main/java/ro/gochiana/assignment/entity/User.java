package ro.gochiana.assignment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "Input should be a valid email")
    private String email;

    @NotEmpty(message = "Input must not be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
