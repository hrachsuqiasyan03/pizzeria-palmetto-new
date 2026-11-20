package am.trainings.palmetto.controller;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.dto.CustomerDto;
import am.trainings.palmetto.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto registerCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getFullName(), customerDto.getUsername());
        String username = customerService.registerCustomer(customer);
        return new CustomerDto(customer.getFullName(), username);
    }

    @GetMapping("/{username}")
    public CustomerDto getCustomer(@PathVariable String username) {
        Customer customer = customerService.findCustomerByUsername(username)
                .orElseThrow (() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        return  new CustomerDto (customer.getFullName (),customer.getUsername ());
    }
}
