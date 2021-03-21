package mx.fmre.rttycontest.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "mx.fmre.rttycontest.persistence.repository" })
@EntityScan(basePackages = "mx.fmre.rttycontest.persistence.model")
@ComponentScan({
	"mx.fmre.rttycontest.api.service",
	"mx.fmre.rttycontest.api.conf",
	"mx.fmre.rttycontest.api.controller",
	"mx.fmre.rttycontest.bs.reports.service",
	"mx.fmre.rttycontest.evaluate.services",
	"mx.fmre.rttycontest.bs.frequency.service",
	"mx.fmre.rttycontest.bs.qsoevaluation.service",
	"mx.fmre.rttycontest.bs.qso.service",
	"mx.fmre.rttycontest.bs.log.service",
	"mx.fmre.rttycontest.bs.email.service",
	"mx.fmre.rttycontest.bs.dxcc.service"})
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
