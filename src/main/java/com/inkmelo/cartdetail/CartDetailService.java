package com.inkmelo.cartdetail;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inkmelo.bookpackage.BookPackage;
import com.inkmelo.bookpackage.BookPackageMode;
import com.inkmelo.bookpackage.BookPackageRepository;
import com.inkmelo.cart.Cart;
import com.inkmelo.customer.Customer;
import com.inkmelo.customer.CustomerRepository;
import com.inkmelo.exception.NoBookPackageFoundException;
import com.inkmelo.exception.NoCartDetailFoundException;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.user.User;
import com.inkmelo.user.UserRepository;
import com.inkmelo.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartDetailService {
	private final CartDetailRepository repository;
	private final CartDetailMappingService mappingService;
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final BookPackageRepository bookPackageRepository;
	private final int DEFAULT_PAGE = 0;
	private final int DEFAULT_VALUE = 5;

	public ResponseEntity<?> findAllCartDetailByStatus(CartDetailStatus status, Integer page, Integer size,
			String username) {
		
		Optional<User> user = userRepository.findByUsername(username);
		
		if (user.isEmpty()) {
			throw new NoUserFoundException("Người dùng không tồn tại.");
		}
		
		Optional<Customer> customerOption = customerRepository.findByUser(user.get());
		
		if (customerOption.isEmpty()) {
			throw new NoCustomerFoundException("Khách hàng không tồn tại.");
		}
		
		Customer customer = customerOption.get();
		
		Cart cart = customer.getCart();
		
		if (page == null & size == null) {
			
			var cartDetails = repository.findAllByCartAndStatus(cart, status);
			
			if (cartDetails.isEmpty()) throw new NoCartDetailFoundException("Giỏ hàng của bạn hiện đang rỗng.");
			
			return new ResponseEntity<>(cartDetails.stream()
					.map(detail -> mappingService.cartDetailToCartDetailResponseDTO(detail))
					.toList(),
					HttpStatus.OK);
			
		} else {
			
			if (page == null) page = DEFAULT_PAGE;
			if (size == null) size = DEFAULT_VALUE;
			
			Pageable paging = PageRequest.of(page, size);
			
			Page<CartDetail> pageCartDetails = repository.findAllByCartAndStatus(cart, status, paging);
			
			var cartDetails = pageCartDetails.getContent();
			
			if (cartDetails.isEmpty()) throw new NoCartDetailFoundException("Giỏ hàng của bạn hiện đang rỗng.");

			var response = cartDetails.stream()
					.map(detail -> mappingService.cartDetailToCartDetailResponseDTO(detail))
					.toList();
			
			
			return Utils.generatePagingListResponseEntity(
					pageCartDetails.getTotalElements(), 
					response, 
					pageCartDetails.getTotalPages(), 
					pageCartDetails.getNumber(), 
					HttpStatus.OK);
			
		}
	}

	public String modifyCartDetails(CartDetailCreateUpdateBodyDTO cartDetailDTO, String username) {
		
		String message = "";
		
		Optional<User> user = userRepository.findByUsername(username);
		
		if (user.isEmpty()) {
			throw new NoUserFoundException("Thêm sản phẩm vào giỏ hàng thất bại. Người dùng không tồn tại.");
		}
		
		Optional<Customer> customerOption = customerRepository.findByUser(user.get());
		
		if (customerOption.isEmpty()) {
			throw new NoCustomerFoundException("Thêm sản phẩm vào giỏ hàng thất bại. Khách hàng không tồn tại.");
		}
		
		Customer customer = customerOption.get();
		
		Cart cart = customer.getCart();
		
		Optional<BookPackage> bookPackageOption = bookPackageRepository.findById(cartDetailDTO.getBookPackageId());
		
		if (bookPackageOption.isEmpty()) {
			throw new NoBookPackageFoundException("Thêm sản phẩm vào giỏ hàng thất bại. Gói tài nguyên sách không tồn tại.");
		}
		
		BookPackage bookPackage = bookPackageOption.get();
		
		Optional<CartDetail> cartDetailOption = repository.findByBookPackageAndCart(bookPackage, cart);
		
		// Cart detail item chưa có sẵn trong db
		if (cartDetailOption.isEmpty()) {
			// Không phải thao tác xóa cart detail item
			if (cartDetailDTO.getQuantity() != 0) {
				// Nếu là gói sách khác ngoài sách bản cứng thì số lượng tối đa sẽ là 1
				if (bookPackage.getMode() != BookPackageMode.PAPER.getValue()) {
					cartDetailDTO.setQuantity(1);
				}

				CartDetail newCartDetail = mappingService.cartDetailCreateBodyDTOToCartDetail(cart, bookPackage, cartDetailDTO.getQuantity());
				
				repository.save(newCartDetail);
				
				message = "Thêm vào giỏ hàng thành công";
			}else {
				message = "Thao tác thất bại. Số lượng sản phẩm không hợp lý.";				
			}
			
//		Cart detail item đã có sẵn trong db
		}else {
			
			CartDetail cartDetail = cartDetailOption.get();
			
//			Không phải thao tác xóa cart detail item
			if (cartDetailDTO.getQuantity() != 0) {
				
				// Nếu là gói sách khác ngoài sách bản cứng thì số lượng tối đa sẽ là 1
				if (bookPackage.getMode() != BookPackageMode.PAPER.getValue()) {
					cartDetailDTO.setQuantity(1);
				}
				
				cartDetail.setQuantity(cartDetailDTO.getQuantity());
				cartDetail.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
//				Cập nhật lại status cho cart detail item phòng trường hợp đang inactive
				cartDetail.setStatus(CartDetailStatus.ACTIVE);
				
				message = "Cập nhập số lượng trong giỏ hàng thành công";
				
//			Là thao tác xóa cart detail item
			}else {
				cartDetail.setQuantity(cartDetailDTO.getQuantity());
				cartDetail.setLastUpdatedTime(Date.valueOf(LocalDate.now()));
				cartDetail.setStatus(CartDetailStatus.INACTIVE);
				
				message = "Xóa khỏi giỏ hàng thành công";
			}
			
			repository.save(cartDetail);
			
		}
		
		return message;
	}
}
