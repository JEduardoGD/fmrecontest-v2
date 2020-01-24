package mx.fmre.rttycontes.recibir.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.recibir.helper.EditionStaticHelper;

@SpringBootTest(classes = EditionStaticHelperTest.class )
@TestPropertySource(locations = "classpath:application.properties")
class EditionStaticHelperTest {
	
	@Test
	void filenameTest() {
		Email email = new Email();
		email.setId(1);
		
		Contest contest = new Contest();
		contest.setId(1);

		Edition edition = new Edition();
		edition.setId(1);
		edition.setContest(contest);

		AttachedFileDTO fileDTO = new AttachedFileDTO();
		fileDTO.setFilename("somefilename");
		
		String testString = String.format("contest_%d/edition_%d/emailcount_%d/%s", 
				edition.getContest().getId(), 
				edition.getId(),
				email.getEmailCount(), 
				fileDTO.getFilename());
		
		assertEquals(testString, EditionStaticHelper.filename(email, edition, fileDTO));
	}
	
	@Test
	void createBucketNameTest() {
		Contest contest = new Contest();
		contest.setId(1);

		Edition edition = new Edition();
		edition.setId(1);
		edition.setContest(contest);

		Integer contestId = edition.getContest().getId();
		Integer editionId = edition.getId();
		
		String testString = "contest_" + contestId.intValue() + "/" + "edition_" + editionId.intValue();;
		
		assertEquals(testString, EditionStaticHelper.createBucketName(edition));
	}
}
