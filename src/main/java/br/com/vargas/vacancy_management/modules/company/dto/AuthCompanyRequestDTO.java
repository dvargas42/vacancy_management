package br.com.vargas.vacancy_management.modules.company.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyRequestDTO {
    
    @NotBlank
    @Pattern(regexp = "\\S+", message = "The username field not should have space between words.")
    private String username;

    @Length(min = 8, message = "The password should have minimun 8 characters.")
    @Length(max = 100, message = "The password should have maximun 100 characters.")
    private String password;
}
