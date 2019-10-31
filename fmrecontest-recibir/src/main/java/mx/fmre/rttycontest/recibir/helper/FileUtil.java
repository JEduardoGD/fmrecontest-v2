package mx.fmre.rttycontest.recibir.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class FileUtil {
	
	private FileUtil() {
		//not called
	}
	
	public static byte[] inputStreamToByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[4096];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return buffer.toByteArray();
	}

	public static String byteArrayToBase64(byte[] byteArray) {
		byte[] encoded = Base64.getEncoder().encode(byteArray);
		return new String(encoded);
	}

	public static InputStream byteArrayToInputStream(byte[] byteArray) {
		return new ByteArrayInputStream(byteArray);
	}

	public static String getMd5Hash(byte[] byteArray) throws IOException {
		try (InputStream is = byteArrayToInputStream(byteArray)) {
			return org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
		}
	}
}
