package mx.fmre.rttycontest.recibir.helper;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class EncryptDecryptStringHelper {

	private SecretKey key;
	private Cipher ecipher;
	private Cipher dcipher;

	public EncryptDecryptStringHelper(String keyString)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		key = new SecretKeySpec(keyString.getBytes(), "AES");
		
		this.ecipher = Cipher.getInstance("AES");
		this.dcipher = Cipher.getInstance("AES");
		
		this.ecipher.init(Cipher.ENCRYPT_MODE, key);
		this.dcipher.init(Cipher.DECRYPT_MODE, key);
	}

	public String encrypt(String message) {
		return privEncrypt(message, this.ecipher);
	}

	public String decrypt(String message) {
		return privDecrypt(message, this.dcipher);
	}

	private static String privEncrypt(String str, Cipher ecipher) {
		try {
			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			enc = BASE64EncoderStream.encode(enc);
			return new String(enc);
		} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

	private static String privDecrypt(String str, Cipher dcipher) {
		try {
			byte[] dec = BASE64DecoderStream.decode(str.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "UTF8");
		} catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
}
