package com.inkmelo.orderdetail;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inkmelo.bookpackage.BookPackage;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	
	List<OrderDetail> findAllByBookPackageIn(Collection<BookPackage> bookPackages);
}
