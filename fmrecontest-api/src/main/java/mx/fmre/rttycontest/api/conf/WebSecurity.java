package mx.fmre.rttycontest.api.conf;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import mx.fmre.rttycontest.api.util.Constants;
import mx.fmre.rttycontest.persistence.repository.IUserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Value("${jwt.secretkey}")
	private String jwtSecretkey;
	
	@Value("${jwt.token.expirationtime}")
	private long jwtTokenExpirationtime;
	
	@Value("${jwt.issuer.info}")
	private String jwtIssuerInfo;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
			"/external/**",
			"/results/**"
    };

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		 * 1. Se desactiva el uso de cookies 2. Se activa la configuración CORS con los
		 * valores por defecto 3. Se desactiva el filtro CSRF 4. Se indica que el login
		 * no requiere autenticación 5. Se indica que el resto de URLs esten securizadas
		 */
		httpSecurity
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.cors().and()
		.csrf().disable()
		.authorizeRequests().antMatchers(HttpMethod.POST, Constants.LOGIN_URL).permitAll()
		.antMatchers(AUTH_WHITELIST).permitAll()
		.anyRequest().authenticated().and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtSecretkey, jwtTokenExpirationtime, jwtIssuerInfo, userRepository, modelMapper))
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtSecretkey));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Se define la clase que recupera los usuarios y el algoritmo para procesar las
		// passwords
		auth.authenticationProvider(authProvider);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}