package am.trainings.palmetto.customer;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class Customer {

    private long customerId;
    private final String fullName;
    private final String username;

    @JsonCreator
    public Customer(@JsonProperty("fullName") String fullName,
                    @JsonProperty("username") String username) {
            this.fullName = fullName;
            this.username = username;
    }

}