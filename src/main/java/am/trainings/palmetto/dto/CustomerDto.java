package am.trainings.palmetto.dto;

public class CustomerDto {
    private final String username;

    public CustomerDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
