package br.com.vargas.vacancy_management.modules.job.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.vargas.vacancy_management.enums.LevelType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListAllJobsRequestDTO implements Pageable {

    @Min(value = 0, message = "Page must be not negative")
    private int page;

    @Max(100)
    private int size;

    private Sort sort = Sort.by(Sort.Order.asc("name"));
    
    @Max(value = 100, message = "Name must have maximum 100 characters")
    private String name;

    @Enumerated(EnumType.STRING)
    private LevelType level;

    @Max(value = 200, message = "Benefits must have maximum 200 characters")
    private String benefits;

    @Max(value = 250, message = "Description must have maximum 250 characters")
    private String description;

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return page * size;
    }

    @Override
    public ListAllJobsRequestDTO next() {
        return new ListAllJobsRequestDTO(page + 1, size, sort, name, level, benefits, description);
    }

    @Override
    public ListAllJobsRequestDTO previousOrFirst() {
        return new ListAllJobsRequestDTO(Math.max(page - 1, 0), size, sort, name, level, benefits, description);
    }

    @Override
    public ListAllJobsRequestDTO first() {
        return new ListAllJobsRequestDTO(0, size, sort, name, level, benefits,description);
    }

    @Override
    public ListAllJobsRequestDTO withPage(int pageNumber) {
        return new ListAllJobsRequestDTO(pageNumber, size, sort, name, level, benefits,description);
    }

    @Override
    public boolean hasPrevious() {
        return page > 0;
    }   
}
