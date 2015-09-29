package com.mongodash.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.mongodash.event.MongoEventBus;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.service.UserService;

@Component("mongoDashAuthenticationProvider")
public class MongoDashAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		String username = auth.getName();
		String password = auth.getCredentials().toString();

		UserDetails userDetails = userService.findUserByUsername(username);

		if (userDetails != null) {
			if (passwordEncoder.matches(password, userDetails.getPassword())) {
				MongoEventBus.post(new Notification(NotificationType.USER_LOGGED_IN, username));
				return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
			} else {
				throw new BadCredentialsException("Authentication Failed. Incorrect user or password.");
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}