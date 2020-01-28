package mx.fmre.rttycontest.recibir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.recibir.services.IMailService;
import mx.fmre.rttycontest.recibir.services.IParserService;
import mx.fmre.rttycontest.recibir.services.IVerifierService;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = { "mx.fmre.rttycontest.persistence.repository" })
@EntityScan(basePackages = "mx.fmre.rttycontest.persistence.model")
@ComponentScan({ 
	"mx.fmre.rttycontest.recibir.services",
	"mx.fmre.rttycontest.recibir.helper",
	"mx.fmre.rttycontest.recibir.business.impl",
	"mx.fmre.rttycontest.bs.parsers.rtty2020",
	"mx.fmre.rttycontest.bs.services"})
@Slf4j
public class RecibirApplication {

	@Autowired private IMailService mailService;
	@Autowired private IVerifierService verifierService;
	@Autowired private IParserService parserService;

	public static void main(String[] args) {
		SpringApplication.run(RecibirApplication.class);
	}

	@Scheduled(cron = "${cron.scanner.expression}")
	public void crontScanContest() {
		log.debug("starting mailService.scanContest()...");
		mailService.scanContest();
		log.debug("ending mailService.scanContest()");
	}

	@Scheduled(cron = "${cron.identify.expression}")
	public void crontIdentifyLogFiles() {
		log.debug("starting mailService.identifyLogFiles()...");
        parserService.identifyLogFiles();
		log.debug("ending mailService.identifyLogFiles()");
	}

	@Scheduled(cron = "${cron.parse.expression}")
	public void cronParseRecivedEmails() {
		log.debug("starting mailService.parseRecivedEmails()...");
        parserService.parseRecivedEmails();
		log.debug("ending mailService.parseRecivedEmails()");
	}

	@Scheduled(cron = "${cron.verify.expression}")
	public void cronVerifyService() {
		log.debug("starting mailService.verifyRecivedEmails()...");
        verifierService.verifyRecivedEmails();
		log.debug("ending mailService.verifyRecivedEmails()");
	}
}
