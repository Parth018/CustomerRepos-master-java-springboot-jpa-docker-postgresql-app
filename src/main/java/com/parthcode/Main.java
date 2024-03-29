package com.parthcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /*
    @GetMapping("/greet")
    public GreetResponse greet(){
        GreetResponse greetResponse = new GreetResponse( "Hello",List.of("Java", "Golang", "Javascript"), new Person("Alex",13,2400)
        );
        return greetResponse;
    }
    record Person (String name, int age, double savings){

    }
    record GreetResponse(
            String greet,
            List<String> programmingLanguages,
            Person person
    ){}
    */

    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }

    record NewcustomerRequest(
            String name,
            String email,
            Integer age
    ){

    }

    @PostMapping
    public void addCustomer(@RequestBody NewcustomerRequest request){
            Customer customer = new Customer();
            customer.setName(request.name);
            customer.setEmail(request.email);
            customer.setAge(request.age);
            customerRepository.save(customer);
    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer id){
        customerRepository.deleteById(id);
    }
   
    @PutMapping("{customerID}")
    public void updateCustomer(@PathVariable("customerID") Integer id, @RequestBody NewcustomerRequest request){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(request.name);
            customer.setEmail(request.email);
            customer.setAge(request.age);
            customerRepository.save(customer);
        } else {
            // customerRepository.find(customer);
            // Handle case when customer is not found with given ID
            // You may throw an exception or return an appropriate response
        }
    }

}
