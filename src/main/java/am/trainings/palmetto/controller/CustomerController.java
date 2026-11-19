package am.trainings.palmetto.controller;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> registerCustomer(
            @RequestBody Customer customer) {

        return ResponseEntity.ok(
                customerService.registerCustomer(customer)
        );
    }
}
