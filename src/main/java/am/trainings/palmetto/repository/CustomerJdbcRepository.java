package am.trainings.palmetto.repository;

import am.trainings.palmetto.customer.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String registerCustomer(Customer customer) {
        jdbcTemplate.update (
                "INSERT INTO customers (full_name, username) VALUES (?, ?)",
                customer.getFullName(),
                customer.getUsername()
        );
        return customer.getUsername();
    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {

        String sql = "SELECT * FROM customers WHERE username = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Customer (
                        rs.getString ("full_name"),
                        rs.getString("username")
                ),
                username
        ).stream().findFirst();
    }


}
