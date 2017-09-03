package com.farfetch.toggle.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {
	
	private static final long serialVersionUID = -5814730319672948860L;

	private String userName;
	
	private String password;
	
	private String userId;
	
	Collection<? extends GrantedAuthority> authorities;
	
	public CustomUserDetail(User user) {
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		for (String role : user.getRoles())
			auths.add(new SimpleGrantedAuthority(role.toUpperCase()));
		
		this.authorities = auths;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}
	
	public String getUserId() {
		return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
