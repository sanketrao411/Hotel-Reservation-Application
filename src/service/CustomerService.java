package service;

import model.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static CustomerService instance;
    private Map<String, Customer> customerMap;

    private CustomerService() {
        customerMap = new HashMap<>();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        if (customerMap.containsKey(email.toLowerCase())) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        Customer customer = new Customer(firstName, lastName, email);
        customerMap.put(email.toLowerCase(), customer);
        System.out.println("Customer added: " + customer);
    }

    public Customer getCustomer(String customerEmail) {
        return customerMap.get(customerEmail.toLowerCase());
    }

    public Collection<Customer> getAllCustomers() {
        return customerMap.values();
    }
}