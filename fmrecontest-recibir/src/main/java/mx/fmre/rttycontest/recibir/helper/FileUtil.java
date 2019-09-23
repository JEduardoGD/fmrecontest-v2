package mx.fmre.rttycontest.recibir.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class FileUtil {
	public static byte[] inputStreamToByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[4096];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		byte[] byteArray = buffer.toByteArray();
		return byteArray;
	}

	public static String byteArrayToBase64(byte[] byteArray) {
		byte[] encoded = Base64.getEncoder().encode(byteArray);
		return new String(encoded);
	}

	public static InputStream byteArrayToInputStream(byte[] byteArray) {
		InputStream targetStream = new ByteArrayInputStream(byteArray);
		return targetStream;
	}

	public static String getMd5Hash(byte[] byteArray) throws IOException {
		try (InputStream is = byteArrayToInputStream(byteArray)) {
			String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
			return md5;
		}
	}
}
