package mx.fmre.rttycontest.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "mx.fmre.rttycontest.persistence.repository" })
@EntityScan(basePackages = "mx.fmre.rttycontest.persistence.model")
@ComponentScan({
	"mx.fmre.rttycontest.web.service",
	"mx.fmre.rttycontest.persistence.service",
	"mx.fmre.rttycontest.web.conf",
	"mx.fmre.rttycontest.web.controller",
	"mx.fmre.rttycontest.web.helper"})
public class RttycontestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RttycontestApplication.class);
	}
}
