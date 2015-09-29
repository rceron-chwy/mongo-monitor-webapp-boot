package com.mongodash.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User extends BaseModel implements UserDetails {

	private static final long serialVersionUID = 1510431445636896248L;

	public static enum fields {
		username, password, email, admin, role;
	}

	@NotNull
	@Size(max = 12, min = 3)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String username;

	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!@#$%\\^\\*\\(\\)]).{6,20})")
	private String password;
	private String currentPassword;
	
	@Size(max = 100, min = 3)
	private String name;

	@NotEmpty
	@Email
	private String email;

	private String role;

	private boolean active;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// ~ UserDetails -----------------------------------------------
	// -------------------------------------------------------------

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return UserRole.valueOf(this.getRole()).getAuthorities();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isActive();
	}

	@Override
	public boolean isEnabled() {
		return this.isActive();
	}
	
	public String getRoleText() {
		return UserRole.valueOf(this.getRole()).getText();
	}
}
