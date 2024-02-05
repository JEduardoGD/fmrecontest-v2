package mx.fmre.rttycontest.api.conf;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.persistence.model.User;
import mx.fmre.rttycontest.persistence.repository.IUserRepository;

@Slf4j
//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired private IUserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user = userRepository.findByEmail(email);

		if (null != user) {
			if(!passwordEncoder.matches(password, user.getPassword())) {
				log.debug("PASSWORD ERROR");
				return null;
			}
            // use the credentials and authenticate against the third-party system return new UsernamePasswordAuthenticationToken( name, password, new ArrayList<>());
            return new UsernamePasswordAuthenticationToken(
            		user.getEmail(), 
            		user.getPassword(),  
            		new ArrayList<>());
        } else {
        	log.debug("USER NULL");
            return null;
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}