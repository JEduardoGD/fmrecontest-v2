package mx.fmre.rttycontest.recibir.helper.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import mx.fmre.rttycontest.recibir.helper.FileUtil;
@SpringBootTest(classes = FileUtilTest.class)
@TestPropertySource(locations = "classpath:application.properties")
public class FileUtilTest {
	
	String string = "UNO DOS TRES CUATRO CINCO";
	InputStream inputStream = new ByteArrayInputStream(string.getBytes());
	String base64String = "VU5PIERPUyBUUkVTIENVQVRSTyBDSU5DTw==";
	String md5Hash = "6277ba921c6c5a177a02f46e12b0b6e7";
	
	@Test
	public void inputStreamToByteArrayTest()  {
		try {
			assertNotNull(FileUtil.inputStreamToByteArray(inputStream));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void byteArrayToBase64Test() {
		assertEquals(base64String, FileUtil.byteArrayToBase64(string.getBytes()));
	}
	
	@Test
	public void byteArrayToInputStreamTest() {
		assertNotNull(FileUtil.byteArrayToInputStream(string.getBytes()));
	}
	
	@Test
	public void getMd5HashTest() {
		try {
			assertEquals(md5Hash, FileUtil.getMd5Hash(string.getBytes()));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
