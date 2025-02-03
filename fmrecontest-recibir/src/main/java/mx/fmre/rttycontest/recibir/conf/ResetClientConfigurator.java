package mx.fmre.rttycontest.recibir.conf;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ResetClientConfigurator {
	// Bean Configuration for RestTemplate
	@Bean
	public RestTemplate restTemplate() {
		log.info("create rest template");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://192.168.0.199:9080"));
		//restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:9080"));
		return restTemplate;
	}
}
