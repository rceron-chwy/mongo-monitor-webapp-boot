package com.mongodash.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

	ROLE_USER("User") {
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
			auths.add(new SimpleGrantedAuthority(ROLE_USER.name()));
			return auths;
		}
	},

	ROLE_MANAGER("Manager") {
		@Override
		Collection<? extends GrantedAuthority> getAuthorities() {
			List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
			auths.add(new SimpleGrantedAuthority(ROLE_USER.name()));
			auths.add(new SimpleGrantedAuthority(ROLE_MANAGER.name()));
			return auths;
		}
	},

	ROLE_ADMIN("Administrator") {
		@Override
		Collection<? extends GrantedAuthority> getAuthorities() {
			List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
			auths.add(new SimpleGrantedAuthority(ROLE_USER.name()));
			auths.add(new SimpleGrantedAuthority(ROLE_MANAGER.name()));
			auths.add(new SimpleGrantedAuthority(ROLE_ADMIN.name()));
			return auths;
		}
	};

	private String text;

	private UserRole(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	// ~
	abstract Collection<? extends GrantedAuthority> getAuthorities();
}
