package br.com.vargas.vacancy_management.enums;

public enum LevelType {
    APPRENTICE("Apprentice"),
    INTERN("Intern"),
    TRAINEE("Trainee"),
    JUNIOR("Junior"),
    MIDDLE("Middle"),
    SENIOR("Senior"),
    SPECIALTIST("Specialtist"),
    NONE("None");

    private String description;

    LevelType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
