package mx.fmre.rttycontest.bs.reports.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import mx.fmre.rttycontest.bs.reports.service.ICsvReportsService;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;

@Service
public class CsvReportsServiceImpl implements ICsvReportsService {

	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private IRelConteoContestLogRepository relConteoContestLogRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	@Autowired private IEmailRepository emailRepository;
	
	@Override
	public byte[] getAllEmailsOnByEditionId(int editionId) {
		Edition edition = editionRepository.findById(editionId).orElse(null);
		List<Email> emails = emailRepository.findByEdition(edition);
		String[] header = { 
				"ID",
				"SUBJECT",
				"FROM NAME",
				"FROM ADDRESS",
				"SENT DATE",
				"VERIFIED AT",
				"ANSWERED AD"};
		List<String[]> listStringsContent = emails.stream().map(email -> {
			String[] content = {
					email.getId()+ "",
					email.getSubject(),
					email.getRecipientsFromName(),
					email.getRecipientsFromAddress(),
					email.getSentDate().toString(),
					email.getVerifiedAt().toString(),
					email.getAnsweredAt().toString()};
			return content;
		}).collect(Collectors.toList());


		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CSVWriter writer = null;
		try {
			OutputStreamWriter streamWriter = new OutputStreamWriter(out);
			// create FileWriter object with file as parameter

			// create CSVWriter object filewriter object as parameter
			writer = new CSVWriter(streamWriter, ';', 
					CSVWriter.NO_QUOTE_CHARACTER, 
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);

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

	@Override
	public byte[] generateRepor(int conteoId) {
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		Integer editionId = conteo.getEdition().getId();
		Edition edition = editionRepository.findById(editionId).orElse(null);
		List<RelConteoContestLog> relConteoContestLogs = relConteoContestLogRepository.findByConteo(conteo);
		String[] header = { "CALLSIGN", "# QSOS" };
		List<String[]> listStringsContent = new ArrayList<>();

		for (RelConteoContestLog relConteoContestLog : relConteoContestLogs) {
			ContestLog contestLog = contestLogRepository.findById(relConteoContestLog.getContestLog().getId())
					.orElse(null);

			List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
			int contestQsoSize = contestQsos.size();

			String[] content = { contestLog.getCallsign(), contestQsos.size() + "" };

			listStringsContent.add(content);
		}

//		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CSVWriter writer = null;
		try {
			OutputStream out = new FileOutputStream("report.csv");
			OutputStreamWriter streamWriter = new OutputStreamWriter(out);
			// create FileWriter object with file as parameter

			// create CSVWriter object filewriter object as parameter
			writer = new CSVWriter(streamWriter, ';', 
					CSVWriter.NO_QUOTE_CHARACTER, 
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);

			writer.writeNext(header);

			for (String[] s : listStringsContent) {
				writer.writeNext(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
//		return out.toByteArray();
		return null;
	}

	public byte[] getBytesOf(Conteo conteo, ContestLog contestLog) {

		RelQsoConteo relQsoConteo;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CSVWriter writer = null;
		try (OutputStreamWriter streamWriter = new OutputStreamWriter(out)) {
			// create FileWriter object with file as parameter

			// create CSVWriter object filewriter object as parameter
			writer = new CSVWriter(streamWriter, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);

			// adding header to csv
			String[] header = { "Name", "Class", "Marks" };
			writer.writeNext(header);

			// add data to csv
			String[] data1 = { "Aman", "10", "620" };
			writer.writeNext(data1);
			String[] data2 = { "Suraj", "10", "630" };
			writer.writeNext(data2);

			// closing writer connection
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public List<byte[]> generateRepors(Edition edition) {
		List<byte[]> listBytes = new ArrayList<>();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CSVWriter writer = null;
		try (OutputStreamWriter streamWriter = new OutputStreamWriter(out)) {
			// create FileWriter object with file as parameter

			// create CSVWriter object filewriter object as parameter
			writer = new CSVWriter(streamWriter, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);

			// adding header to csv
			String[] header = { "Name", "Class", "Marks" };
			writer.writeNext(header);

			// add data to csv
			String[] data1 = { "Aman", "10", "620" };
			writer.writeNext(data1);
			String[] data2 = { "Suraj", "10", "630" };
			writer.writeNext(data2);

			// closing writer connection
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return listBytes;
	}

	public void zip() {
		try {
			List<String> srcFiles = Arrays.asList("test1.txt", "test2.txt");
			FileOutputStream fos;
			fos = new FileOutputStream("multiCompressed.zip");
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			for (String srcFile : srcFiles) {
				File fileToZip = new File(srcFile);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
