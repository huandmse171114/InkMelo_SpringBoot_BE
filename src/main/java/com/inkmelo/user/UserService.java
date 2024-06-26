package com.inkmelo.user;

import java.lang.StackWalker.Option;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inkmelo.cart.Cart;
import com.inkmelo.cart.CartRepository;
import com.inkmelo.customer.Customer;
import com.inkmelo.customer.CustomerMappingService;
import com.inkmelo.customer.CustomerRepository;
import com.inkmelo.exception.NoCustomerFoundException;
import com.inkmelo.exception.NoPublisherExistException;
import com.inkmelo.exception.NoUserExistException;
import com.inkmelo.exception.NoUserFoundException;
import com.inkmelo.exception.PasswordConfirmIsDifferentException;
import com.inkmelo.mailsender.SendEmailService;
import com.inkmelo.publisher.PublisherAdminResponseDTO;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	private final UserMappingService mappingService;
	private final CustomerRepository customerRepository;
	private final CustomerMappingService customerMappingService;
	private final CartRepository cartRepository;
	private final SendEmailService sendEmailService;

	public User loadUserByUsername(String username) {
		Optional<User> userOptional = repository.findByUsername(username);
		
		if (userOptional.isEmpty()) {
			return null;
		}
		
		return userOptional.get();
	}

	public void createUser(User user) {
		repository.save(user);
		
		if (user.getRole() == UserRole.CUSTOMER) {
			Customer customer = Customer.builder()
					.fullname(user.getFullname())
					.email(user.getEmail())
					.user(user)
					.createdAt(Date.valueOf(LocalDate.now()))
					.lastChangedBy("HUANDM")
					.lastUpdatedTime(Date.valueOf(LocalDate.now()))
					.build();
			
			customerRepository.save(customer);
			
			Optional<Customer> customerDB = customerRepository.findByUser(user);
			
			if (customerDB.isEmpty()) {
				throw new NoCustomerFoundException("Đăng ký tài khoản thất bại. Tạo mới thông tin khách hàng không thành công.");
			}
			
			Cart cart = Cart.builder()
					.customer(customerDB.get())
					.build();
			
			cartRepository.save(cart);
			
		}
	}

	public void saveUser(UserCreateBodyDTO userDTO) throws DataIntegrityViolationException,
		PasswordConfirmIsDifferentException {
		if (!userDTO.confirmPassword().equals(userDTO.password())) {
			throw new PasswordConfirmIsDifferentException("Password Confirm is different.");
		}
		User user = mappingService.userCreateBodyDTOToUser(userDTO);
		
		repository.save(user);
		
		if (user.getRole() == UserRole.CUSTOMER) {
			Customer customer = customerMappingService.userToCustomer(user);
			customerRepository.save(customer);
			
			Optional<Customer> customerDB = customerRepository.findByUser(user);
			
			if (customerDB.isEmpty()) {
				throw new NoCustomerFoundException("Đăng ký tài khoản thất bại. Tạo mới thông tin khách hàng không thành công.");
			}
			
			Cart cart = Cart.builder()
					.customer(customerDB.get())
					.build();
			
			cartRepository.save(cart);
			
		}
		
	}

	public List<UserAdminResponseDTO> findAllUsers() {
		var users = repository.findAll();
		
		if (users.isEmpty()) {
			throw new NoUserExistException("User data is empty.");
		}
		
		return users.stream()
				.map(user -> 
					mappingService
						.userToUserAdminResponseDTO(user))
				.sorted(new Comparator<UserAdminResponseDTO>() {
					@Override
					public int compare(UserAdminResponseDTO o1, UserAdminResponseDTO o2) {
						return o1.id().compareTo(o2.id());
					}
				})
				.toList();
	}
	
//	public User getByResetPasswordToken(String token) throws NoUserFoundException {
//		Optional<User> userOption = repository.findByResetPasswordToken(token);
//		
//		if (userOption.isEmpty()) {
//			throw new NoUserFoundException("User is not found.");
//		}
//
//		return userOption.get();
//	}
//	
//	public void updatePassword(User user, String newPassword) {
//		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
//		String encodedPassword = pwdEncoder.encode(newPassword);
//		user.setPassword(encodedPassword);
//		
//		user.setResetPasswordToken(null);
//		repository.save(user);
//	}
//	
//	public void updateResetPasswordToken(String token, String username) throws NoUserFoundException {
//		Optional<User> userOption = repository.findByUsername(username);
//		
//		if (userOption.isEmpty()) {
//			throw new NoUserFoundException("User is not found.");
//		}
//		
//		User user = userOption.get();
//		user.setResetPasswordToken(token);
//		repository.save(user);
//	}
	public void updateResetPasswordToken(String email) throws NoUserFoundException, MessagingException {
        Optional<User> userOption = repository.findByEmail(email);

        if (userOption.isEmpty()) {
            throw new NoUserFoundException("User is not found.");
        }

        User user = userOption.get();
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiry(Date.valueOf(LocalDate.now().plusDays(1)));
        repository.save(user);

        try {
            sendEmailService.sendResetPasswordEmail(user.getEmail(), token);
        } catch (MessagingException e) {
            throw new MessagingException("Failed to send password reset email.");
        }
    }

    public User getByResetPasswordToken(String token) throws NoUserFoundException {
        Optional<User> userOption = repository.findByResetPasswordToken(token);

        if (userOption.isEmpty() || userOption.get().getResetPasswordTokenExpiry().before(Date.valueOf(LocalDate.now()))) {
            throw new NoUserFoundException("Invalid or expired reset token.");
        }

        return userOption.get();
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String encodedPassword = pwdEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        repository.save(user);
    }
}
