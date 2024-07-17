package com.inkmelo.cartdetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.inkmelo.cart.Cart;
import com.inkmelo.bookpackage.BookPackage;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
	
	List<CartDetail> findAllByCartAndStatusOrderByIdAsc(Cart cart, CartDetailStatus status);
	
	Page<CartDetail> findAllByCartAndStatusOrderByIdAsc(Cart cart, CartDetailStatus status, Pageable pageable);
	
	Optional<CartDetail> findByBookPackageAndCartAndStatus(BookPackage bookPackage, Cart cart, CartDetailStatus status);
	
	Optional<CartDetail> findByBookPackageAndCart(BookPackage bookPackage, Cart cart);
	
	List<CartDetail> findAllByIdInOrderByIdAsc(Collection<Integer> id);
	
	List<CartDetail> findAllByStatusAndIdInOrderByIdAsc(CartDetailStatus status, Collection<Integer> id);
}
