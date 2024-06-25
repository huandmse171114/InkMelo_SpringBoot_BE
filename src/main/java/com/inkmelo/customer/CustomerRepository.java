package com.inkmelo.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inkmelo.user.User;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Optional<Customer> findByUser(User user);
}
