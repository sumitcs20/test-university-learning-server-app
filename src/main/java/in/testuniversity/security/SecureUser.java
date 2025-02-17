package in.testuniversity.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.testuniversity.entity.User;

public class SecureUser implements UserDetails{

	private static final long serialVersionUID = 1L;
	private final User user;
	
	public SecureUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
	    return this.user;
	}
	
	@Override
	public String getUsername() {
		return user.getEmailId() != null ? user.getEmailId() : user.getMobile(); // fetch email or mobile
	}
	
	@Override
	public String getPassword() {		
		return user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Dynamically assign authority based on the user's role.
        // Assuming user.getRole().getRole() returns a String like "USER", "INSTRUCTOR", or "ADMIN".
		String roleName = user.getRole() != null ? user.getRole().getRole() : "USER";
		return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
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
