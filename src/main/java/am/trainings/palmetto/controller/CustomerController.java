package am.trainings.palmetto.controller;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.dto.CustomerDto;
import am.trainings.palmetto.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto registerCustomer(
            @RequestBody Customer customer) {

        return new CustomerDto (customerService.registerCustomer(customer));
    }
}
