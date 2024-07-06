package br.com.vargas.vacancy_management.modules.job;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.vargas.vacancy_management.enums.LevelType;
import br.com.vargas.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.vargas.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.vargas.vacancy_management.modules.job.dto.CreateJobDTO;
import br.com.vargas.vacancy_management.utils.TesteUtils;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Value("${security.token.secret}")
    private String secretKey;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }
    
    @Test
    public void shouldBeAbleToCreateANewJob() throws Exception {
        CompanyEntity company = CompanyEntity.builder()
            .description("COMPANY_DESCRIPTION")
            .email("email@campany.com")
            .password("123456789012")
            .username("COMPANY_USERNAME")
            .name("COMPANY_NAME")
            .build();
        
        company = companyRepository.saveAndFlush(company);

        CreateJobDTO createJobDTO = CreateJobDTO.builder()
            .benefits("BENEFITS_TEST")
            .description("DESCRIPTION_TEST")
            .level(LevelType.JUNIOR)
            .build();   
        
        String object = TesteUtils.objectToJson(createJobDTO).toString();

        mvc.perform(MockMvcRequestBuilders.post("/job/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(object)
            .header("Authorization", 
                TesteUtils.generateToken(company.getId(), secretKey)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
        CreateJobDTO createJobDTO = CreateJobDTO.builder()
            .benefits("BENEFITS")
            .description("DESCRIPTION_TEST")
            .level(LevelType.JUNIOR)
            .build();
        
        String object = TesteUtils.objectToJson(createJobDTO).toString();

        mvc.perform(MockMvcRequestBuilders.post("/job/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(object)
            .header("Authorization", TesteUtils.generateToken(UUID.randomUUID(), secretKey)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    
    }
}
