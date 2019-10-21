package mx.fmre.rttycontest.recibir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import mx.fmre.rttycontest.recibir.services.IMailService;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = { "mx.fmre.rttycontest.persistence.repository" })
@EntityScan(basePackages = "mx.fmre.rttycontest.persistence.model")
@ComponentScan({ 
	"mx.fmre.rttycontest.persistence.service", 
	"mx.fmre.rttycontest.recibir.services",
	"mx.fmre.rttycontest.recibir.helper" })
public class RecibirApplication {

	@Autowired
	private IMailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(RecibirApplication.class, args);
	}

	@Scheduled(cron = "${cron.expression}")
	public void scheduleTaskUsingCronExpression() {

		long now = System.currentTimeMillis() / 1000;
		System.out.println("schedule tasks using cron jobs - " + now);

		mailService.scanContest();
	}

}
