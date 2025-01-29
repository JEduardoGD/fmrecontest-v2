package mx.fmre.rttycontest.recibir.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://192.168.0.199:9080"));
		return restTemplate;
	}
}
