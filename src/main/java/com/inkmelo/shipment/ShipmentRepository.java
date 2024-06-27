package com.inkmelo.shipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.inkmelo.customer.Customer;


public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
	
	List<Shipment> findAllByStatusAndCustomer(ShipmentStatus status, Customer customer);
	
	Page<Shipment> findAllByStatusAndCustomer(ShipmentStatus status, Customer customer, Pageable pageable);
}
