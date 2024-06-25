package br.com.vargas.vacancy_management.modules.company.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequestDTO {
    
    @Length(max = 200, message = "Name must not have more than 200 characters.")
    private String name;

    @Pattern(regexp = "^[a-z0-9_-]+$", message = "Username must not have special characters.")
    @Length(max = 100, message = "Username must not have more than 100 characters.")
    private String username;

    @Email
    @Length(max = 100, message = "email must not have more than 100 characters.")
    private String email;

    @Length(max = 250, message = "Description must not have more than 250 characters.")
    private String description;

    @Length(max = 16, message = "Password must have maximum 16 charactes.")
    @Length(min = 8, message = "Password must have minimum 8 characteres.")
    private String password;

    @Length(max = 16, message = "Password must have maximum 16 charactes.")
    @Length(min = 8, message = "Password must have minimum 8 characteres.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?!.*\\/).{8,}$",
        message = "Password must have minimum one special caracter, one number and one letter.")
    private String newPassword;

    @Length(max = 100, message = "Website must not have more than 100 characters.")
    private String website;
}
