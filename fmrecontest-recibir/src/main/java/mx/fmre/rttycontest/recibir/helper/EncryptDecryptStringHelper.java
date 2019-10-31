package mx.fmre.rttycontest.recibir.helper;

import java.nio.charset.StandardCharsets;
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

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.exception.FmreContestException;

@Slf4j
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

	public String encrypt(String message) throws FmreContestException {
		return privEncrypt(message, this.ecipher);
	}

	public String decrypt(String message) throws FmreContestException {
		return privDecrypt(message, this.dcipher);
	}

	private static String privEncrypt(String str, Cipher ecipher) throws FmreContestException {
		try {
			byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);
			byte[] enc = ecipher.doFinal(utf8);
			enc = BASE64EncoderStream.encode(enc);
			return new String(enc);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			log.error(e.getLocalizedMessage());
			throw new FmreContestException(e.getLocalizedMessage());
		}
	}

	private static String privDecrypt(String str, Cipher dcipher) throws FmreContestException {
		try {
			byte[] dec = BASE64DecoderStream.decode(str.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, StandardCharsets.UTF_8);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			log.error(e.getLocalizedMessage());
			throw new FmreContestException(e.getLocalizedMessage());
		}
	}
}
