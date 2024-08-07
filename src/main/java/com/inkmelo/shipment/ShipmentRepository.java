package com.inkmelo.shipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.inkmelo.customer.Customer;


public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
	
	List<Shipment> findAllByStatusAndCustomer(ShipmentStatus status, Customer customer);
	
	Page<Shipment> findAllByStatusAndCustomer(ShipmentStatus status, Customer customer, Pageable pageable);
	
	Optional<Shipment> findByCustomerAndIsDefault(Customer customer, boolean isDefault);
	
	List<Shipment> findAllByCustomerAndIdNot(Customer customer, Integer id);
}
