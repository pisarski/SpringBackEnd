package com.lumesse.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lumesse.entity.User;
import com.lumesse.entity.enums.UserRight;

/**
 * Stores information about logged user.
 */
public class SpittleUserDetails implements UserDetails {

	private static final long serialVersionUID = -5584512166922680774L;

	private User user;
	private List<GrantedAuthority> authorities = new ArrayList<>();

	public SpittleUserDetails(User user) {
		this.user = user;
		initAuthoritiesCollection(user);

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.isEnabled();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	public User getUser() {
		return user;
	}

	private void initAuthoritiesCollection(User user) {
		Set<UserRight> rights = user.getRights();
		if (rights == null) {
			return;
		}

		for (UserRight right : rights) {
			authorities.add(new SimpleGrantedAuthority(right.name()));
		}
	}
}
