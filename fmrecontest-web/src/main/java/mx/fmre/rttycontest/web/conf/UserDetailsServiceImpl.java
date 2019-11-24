package mx.fmre.rttycontest.web.conf;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.UserRole;
import mx.fmre.rttycontest.persistence.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		mx.fmre.rttycontest.persistence.model.User user = userRepository.findByEmail(email);
		
		List<SimpleGrantedAuthority> listGrantedAuthorities = user.getUserRoles().stream()
				.map(UserRole::getRole)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		return (UserDetails) new User(user.getEmail(), user.getPassword(), listGrantedAuthorities);
	}
}