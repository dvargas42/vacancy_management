package br.com.vargas.vacancy_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import br.com.vargas.vacancy_management.modules.job.entities.JobEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "The username field not should have space between words.")
    private String username;

    @Email(message = "The field e-mail should have a valid e-mail.")
    private String email;

    @Length(min = 8, message = "The password should have minimun 8 characters.")
    @Length(max = 100, message = "The password should have maximun 100 characters.")
    private String password;

    private String website;

    private String name;
    
    private String description;

    @OneToMany(mappedBy = "companyEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobEntity> jobs;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
