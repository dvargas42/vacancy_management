package br.com.vargas.vacancy_management.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCompanyResponseDTO {

    private String access_token;
    private Long expires_in;
    private String refresh_token;
}
