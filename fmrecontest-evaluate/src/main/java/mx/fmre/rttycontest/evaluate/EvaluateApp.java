package mx.fmre.rttycontest.evaluate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.service.ExternalDxccService;
import mx.fmre.rttycontest.bs.dxcc.util.DxccUtil;
import mx.fmre.rttycontest.evaluate.services.ICompleteDxccService;
import mx.fmre.rttycontest.evaluate.services.IEvaluateService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
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
	"mx.fmre.rttycontest.bs.dxcc.service",
	"mx.fmre.rttycontest.bs.gridlocator.service",
	"mx.fmre.rttycontest.bs.location.service"})
@Slf4j
public class EvaluateApp implements CommandLineRunner {
	
	@Autowired ICompleteDxccService completeDxccService;
	@Autowired IEvaluateService evaluateService;
	@Autowired private IDxccEntityRepository       dxccEntityRepository;
    @Autowired private ExternalDxccService         externalDxccService;
    
    @Value("${FMRE_CALLSIGN}")
    private String xe1lmCallsign;
    
    @Value("${spring.datasource.url}")
    private String springDatasourceUrl;
    @Value("${spring.datasource.username}")
    private String springDatasourceUsername;
    @Value("${spring.datasource.password}")
    private String springDatasourcePassword;
    @Value("${email.password.encodingkey}")
    private String emailPasswordEncodingkey;
    @Value("${amazon.creds.secretkey}")
    private String amazonCredsSecretkey;
    @Value("${amazon.creds.acceskey}")
    private String amazonCredsAcceskey;
    @Value("${amazon.s3.bucketname}")
    private String amazonS3Bucketname;
    @Value("${file.manager.impl}")
    private String fileManagerImpl;
    @Value("${jwt.secretkey}")
    private String jwtSecretkey;
    @Value("${jwt.token.expirationtime}")
    private String jwtTokenExpirationtime;
    @Value("${jwt.issuer.info}")
    private String jwtIssuerInfo;
    

	@Autowired private IEditionRepository editionRepository;

	public static void main(String[] args) {
		SpringApplication.run(EvaluateApp.class);
	}

	@Override
	public void run(String... args) throws Exception {
	    log.info(springDatasourceUrl);
	    log.info(springDatasourceUsername);
	    log.info(springDatasourcePassword);
	    log.info(emailPasswordEncodingkey);
	    log.info(amazonCredsSecretkey);
	    log.info(amazonCredsAcceskey);
	    log.info(amazonS3Bucketname);
	    log.info(fileManagerImpl);
	    log.info(jwtSecretkey);
	    log.info(jwtTokenExpirationtime);
	    log.info(jwtIssuerInfo);
	    
	    
		completeDxccService.completeDxccEntityQsos();
		completeDxccService.completeDxccEntityLogs();
		completeDxccService.completeBandsOnQsos();

		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
            DxccEntity mexicoDxccEntity = null;
            Map<String, DxccEntity> map = DxccUtil.fillDxccMap(dxccEntityRepository, edition);
            try {
                mexicoDxccEntity = DxccUtil.getDxccOf(externalDxccService, map, xe1lmCallsign, edition);
            } catch (FmreContestException e1) {
                log.error(e1.getLocalizedMessage());
            }
		    
			String description = "conteo for edition id " + edition.getId();
			Conteo conteo = evaluateService.createConteo(edition, description);
			evaluateService.findForErrorsOnQsos(conteo, edition);
            evaluateService.setPointsForQssos(mexicoDxccEntity, conteo);
            evaluateService.setMultipliesQsos(conteo);
			evaluateService.evaluateActiveEditions(conteo);
		}
	}
}


























