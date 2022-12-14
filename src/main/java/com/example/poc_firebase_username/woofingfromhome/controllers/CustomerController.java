package com.example.poc_firebase_username.woofingfromhome.controllers;

import com.example.poc_firebase_username.woofingfromhome.models.Customer;
import com.example.poc_firebase_username.woofingfromhome.models.Helpers;
import com.example.poc_firebase_username.woofingfromhome.repositories.CustomerRepository;
import com.example.poc_firebase_username.woofingfromhome.repositories.MatchRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MatchRepository matchRepository;

    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/customers/{id}")
    public ResponseEntity getCustomers(@PathVariable String id) {
        return new ResponseEntity<>(customerRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer) throws InterruptedException {
        customerRepository.save(customer);
        Helpers.generateMatches(customer,customerRepository.findAll(),matchRepository);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer){
        customerRepository.save(customer);
        Helpers.updateMatchTable(customer,matchRepository.findAll(),matchRepository);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity deleteCustomer(@PathVariable String id) {
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
