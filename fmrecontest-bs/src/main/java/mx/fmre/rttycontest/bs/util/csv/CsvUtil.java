package mx.fmre.rttycontest.bs.util.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;

public class CsvUtil {
	
	private final static DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH);
	
	public static List<String[]> listEmailsToStrings(
			List<Email> emails,
			List<LastEmail> lastEmails,
			ICatEmailErrorRepository catEmailErrorRepository,
			Map<Integer, String> mapEmmailError,
			Map<Integer, String> emailStatusesArray) {
		List<String[]> listStringsContent = emails.stream().map(email -> {
			boolean usedForCount = false;
			if(lastEmails.stream().filter(l -> l.getEmailId().equals(email.getId())).findFirst().orElse(null) != null) {
				usedForCount = true;
			}
			List<CatEmailError> emailErrors = catEmailErrorRepository.getErrorsOfEmail(email);
			List<String> errors = emailErrors
					.stream()
					.map(x -> mapEmmailError.get(x.getId()))
					.collect(Collectors.toList());
			String[] content = {
					email.getId() + "",
					email.getSubject(),
					email.getRecipientsFromName(),
					email.getRecipientsFromAddress(),
					df.format(email.getSentDate()),
					df.format(email.getVerifiedAt()),
					df.format(email.getAnsweredAt()),
					emailStatusesArray.get(email.getEmailStatus().getId()),
					usedForCount ? "USED" : "",
					String.join(";", errors)};
			return content;
		}).collect(Collectors.toList());
		return listStringsContent;
	}
	
	public static byte[] createCsvByteArray(String[] header, List<String[]> listStringsContent) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CSVWriter writer = null;
		try {
			OutputStreamWriter streamWriter = new OutputStreamWriter(out);
			writer = new CSVWriter(streamWriter);
			writer.writeNext(header);
			for (String[] s : listStringsContent) {
				writer.writeNext(s);
			}
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return out.toByteArray();
	}
}
