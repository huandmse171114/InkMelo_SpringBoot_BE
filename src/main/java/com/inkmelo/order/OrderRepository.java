package com.inkmelo.order;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inkmelo.customer.Customer;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("SELECT CASE WHEN COUNT(od) > 0 THEN TRUE ELSE FALSE END " +
	           "FROM OrderDetail od " +
	           "JOIN od.order o " +
	           "JOIN od.bookPackage bp " +
	           "JOIN bp.book b " +
	           "WHERE o.customer.id = :customerId AND b.id = :bookId")
	    boolean existsByCustomerIdAndBookId(@Param("customerId") Integer customerId, @Param("bookId") Integer bookId);
	
	Page<Order> findAllByCustomerAndStatus(Customer customer, OrderStatus status, Pageable pageable);
	
	}
