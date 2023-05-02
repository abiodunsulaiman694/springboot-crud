package com.abiodun;

import com.abiodun.customer.Customer;
import com.abiodun.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        Customer daniel = new Customer(
                "Daniel",
                "daniel@abiodun.dev",
                10
        );
        Customer joshua = new Customer(
                "Joshua",
                "joshua@abiodun.dev",
                5
        );
        List<Customer> customers = List.of(daniel, joshua);
        customerRepository.saveAll(customers);
        return  args -> {

        };
    }

}
