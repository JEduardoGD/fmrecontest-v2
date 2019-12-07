package mx.fmre.rttycontest.api.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mx.fmre.rttycontest.api.dto.UserDTO;
import mx.fmre.rttycontest.api.model.Usuario;
import mx.fmre.rttycontest.api.util.Constants;
import mx.fmre.rttycontest.persistence.model.User;
import mx.fmre.rttycontest.persistence.repository.IUserRepository;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	private String jwtSecretkey;
	private long jwtTokenExpirationtime;
	private String jwtIssuerInfo;
	private IUserRepository userRepository;
    private ModelMapper modelMapper;

	public JWTAuthenticationFilter(
			AuthenticationManager authenticationManager,
			String jwtSecretkey,
			long jwtTokenExpirationtime,
			String jwtIssuerInfo,
			IUserRepository userRepository,
			ModelMapper modelMapper) {
		this.authenticationManager = authenticationManager;
		this.jwtSecretkey = jwtSecretkey;
		this.jwtTokenExpirationtime = jwtTokenExpirationtime;
		this.jwtIssuerInfo = jwtIssuerInfo;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Usuario credenciales = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			
			return authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							credenciales.getEmail(), 
							credenciales.getPassword(), 
							new ArrayList<>()
							));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		String token = Jwts.builder()
				.setIssuedAt(new Date())
				.setIssuer(jwtIssuerInfo)
				.setSubject(auth.getName())
				.setExpiration(new Date(System.currentTimeMillis() + jwtTokenExpirationtime))
				.signWith(SignatureAlgorithm.HS512, jwtSecretkey).compact();
		
		User user = userRepository.findByEmail(auth.getName());
		Gson gson = new Gson();
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		userDTO.setToken(token);

		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(gson.toJson(userDTO));
		response.getWriter().flush();
		response.getWriter().close();
		
		response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);
		
	}
}
