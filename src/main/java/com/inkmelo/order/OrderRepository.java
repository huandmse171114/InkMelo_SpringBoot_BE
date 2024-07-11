package com.inkmelo.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("SELECT CASE WHEN COUNT(od) > 0 THEN TRUE ELSE FALSE END " +
	           "FROM OrderDetail od " +
	           "JOIN od.order o " +
	           "JOIN od.bookPackage bp " +
	           "JOIN bp.book b " +
	           "WHERE o.customer.id = :customerId AND b.id = :bookId")
	    boolean existsByCustomerIdAndBookId(@Param("customerId") Integer customerId, @Param("bookId") Integer bookId);
	}
