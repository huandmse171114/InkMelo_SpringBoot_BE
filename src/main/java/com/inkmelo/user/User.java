package com.inkmelo.user;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
	
	@Id
	@GeneratedValue
	private Integer id;

	@Column(unique = true, nullable = false, length = 100)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(length = 150)
	private String fullname;

	@Column(unique = true)
	private String email;

	private String resetPasswordToken;

	private Date resetPasswordTokenExpiry;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private UserRole role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private UserStatus status;

	@Column(updatable = false, nullable = false)
	private Date createdAt;

	@Column(nullable = false)
	private Date lastUpdatedTime;

	@Column(nullable = false, length = 100)
	private String lastChangedBy;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.toString()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return status == UserStatus.ACTIVE;
	}

	@Override
	public boolean isAccountNonLocked() {
		return status == UserStatus.ACTIVE;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return status == UserStatus.ACTIVE;
	}

	@Override
	public boolean isEnabled() {
		return status == UserStatus.ACTIVE;
	}
}
