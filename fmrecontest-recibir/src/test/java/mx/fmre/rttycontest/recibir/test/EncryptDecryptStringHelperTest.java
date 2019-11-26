package mx.fmre.rttycontest.recibir.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.recibir.helper.EncryptDecryptStringHelper;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class EncryptDecryptStringHelperTest {
	private String clearText = "UNOdosTRES456789";
	private String keyEncript = "XxZzAaSsTtYyUuOo";
	private String encriptedText = "XPxTNn286RMCWd3h4xK1PUS0/e6Di9SgDu/C/ToOSOk=";

	@Test
	void encryptTest() {
		try {
			EncryptDecryptStringHelper encryptDecryptStringHelper = new EncryptDecryptStringHelper(keyEncript);
			assertEquals(encriptedText, encryptDecryptStringHelper.encrypt(clearText));
		} catch (FmreContestException e) {
			fail(e.getLocalizedMessage());
		}
	}
}
