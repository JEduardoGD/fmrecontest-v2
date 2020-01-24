package mx.fmre.rttycontest.recibir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.recibir.services.IParserService;

//@SpringBootApplication
//@EnableScheduling
//@EnableJpaRepositories(basePackages = { "mx.fmre.rttycontest.persistence.repository" })
//@EntityScan(basePackages = "mx.fmre.rttycontest.persistence.model")
//@ComponentScan({ 
//	"mx.fmre.rttycontest.recibir.services",
//	"mx.fmre.rttycontest.recibir.helper" })
//@Slf4j
//public class RecibirApplication {
//
//	@Autowired
//	private IMailService mailService;
//
//	public static void main(String[] args) {
//		SpringApplication.run(RecibirApplication.class);
//	}
//
//	@Scheduled(cron = "${cron.expression}")
//	public void scheduleTaskUsingCronExpression() {
//
//		long now = System.currentTimeMillis() / 5000;
//		
//		log.info("schedule tasks using cron jobs - {}", now);
//
//		mailService.scanContest();
//	}
//
//}

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "mx.fmre.rttycontest.persistence.repository" })
@EntityScan(basePackages = "mx.fmre.rttycontest.persistence.model")
@ComponentScan({ 
	"mx.fmre.rttycontest.recibir.services",
	"mx.fmre.rttycontest.recibir.helper" })
@Slf4j
public class RecibirApplication implements CommandLineRunner {

	private @Autowired IParserService parserService;
 
    public static void main(String[] args) {
        SpringApplication.run(RecibirApplication.class, args);
    }
	  
    @Override
    public void run(String... args) {
        log.info("EXECUTING : command line runner");
        parserService.verifyRecivedEmails();
        log.info("END : command line runner");
    }
}
