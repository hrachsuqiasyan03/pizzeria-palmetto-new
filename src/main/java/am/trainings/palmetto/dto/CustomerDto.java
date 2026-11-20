package am.trainings.palmetto.dto;

public class CustomerDto {

    private final String fullName;
    private final String username;


    public CustomerDto(String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }
}
