package com.inkmelo.order;

import java.util.List;
import java.util.Optional;
import java.sql.Date;

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
	@Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.status = :status")
    List<Order> findByCustomerIdAndStatus(@Param("customerId") Integer customerId, @Param("status") OrderStatus status);
	
	Optional<Order> findByIdAndCustomerIdAndStatus(Integer orderId, Integer customerId, OrderStatus status);
	Page<Order> findAllByCustomerAndStatusAndCreatedAtBetweenOrderByCreatedAtDesc(Customer customer, OrderStatus status, Date startDate, Date endDate, Pageable pageable);

	}
