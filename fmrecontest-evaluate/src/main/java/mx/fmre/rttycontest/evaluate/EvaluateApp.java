package mx.fmre.rttycontest.evaluate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import mx.fmre.rttycontest.evaluate.services.ICompleteDxccService;
import mx.fmre.rttycontest.evaluate.services.IEvaluateService;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;

@SpringBootApplication

@EnableJpaRepositories(basePackages = { "mx.fmre.rttycontest.persistence.repository" })
@EntityScan(basePackages = "mx.fmre.rttycontest.persistence.model")
@ComponentScan({
	"mx.fmre.rttycontest.persistence.repository",
	"mx.fmre.rttycontest.evaluate.services",
	"mx.fmre.rttycontest.bs.dxcc.service",
	"mx.fmre.rttycontest.bs.frequency.service", 
	"mx.fmre.rttycontest.bs.qsoevaluation.service",
	"mx.fmre.rttycontest.bs.dxcc.service"})
public class EvaluateApp implements CommandLineRunner {
	
	@Autowired ICompleteDxccService completeDxccService;
	@Autowired IEvaluateService evaluateService;

	@Autowired private IEditionRepository editionRepository;

	public static void main(String[] args) {
		SpringApplication.run(EvaluateApp.class);
	}

	@Override
	public void run(String... args) throws Exception {
		completeDxccService.completeDxccEntityQsos();
		completeDxccService.completeDxccEntityLogs();
		completeDxccService.completeBandsOnQsos();
		
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			String description = "conteo for edition id " + edition.getId();
			Conteo conteo = evaluateService.createConteo(edition, description);
			evaluateService.findForErrorsOnQsos(conteo, edition);
			evaluateService.setPointsForQssos(conteo);
			evaluateService.setMultipliesQsos(conteo);
			evaluateService.evaluateActiveEditions(conteo);
		}
	}
}


























