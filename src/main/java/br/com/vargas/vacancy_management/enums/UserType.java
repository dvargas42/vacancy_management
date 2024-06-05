package br.com.vargas.vacancy_management.enums;

public enum UserType {
    CANDIDATE("candidate_id"),
    COMPANY("company_id");

    private String description;

    UserType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
