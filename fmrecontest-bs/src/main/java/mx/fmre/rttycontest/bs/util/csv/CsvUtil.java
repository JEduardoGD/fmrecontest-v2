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

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.parsers.rtty2020.QsoParserRtty2020ServiceImpl;
import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;

@Slf4j
public class CsvUtil {
	
	public static List<String[]> listEmailsToStrings(
			List<Email> emails,
			List<LastEmail> lastEmails,
			ICatEmailErrorRepository catEmailErrorRepository,
			Map<Integer, String> mapEmmailError,
			Map<Integer, String> emailStatusesArray) {
		List<String[]> listStringsContent = emails.stream().map(email -> {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH);
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
					email.getEmailCount() + "",
					email.getSubject(),
					email.getRecipientsFromName(),
					email.getRecipientsFromAddress(),
					email.getSentDate() != null ? df.format(email.getSentDate()) : null,
					email.getVerifiedAt() != null ? df.format(email.getVerifiedAt()) : null,
					email.getAnsweredAt() != null ? df.format(email.getAnsweredAt()) : null,
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
					log.error(e.getLocalizedMessage());
				}
		}
		return out.toByteArray();
	}
}
