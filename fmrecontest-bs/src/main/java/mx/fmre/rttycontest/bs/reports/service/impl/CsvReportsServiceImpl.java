package mx.fmre.rttycontest.bs.reports.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.opencsv.CSVWriter;

import mx.fmre.rttycontest.bs.reports.service.ICsvReportsService;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;

public class CsvReportsServiceImpl implements ICsvReportsService {

	@Override
	public byte[] generateRepor(Edition edition) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CSVWriter writer = null;
		try (OutputStreamWriter streamWriter = new OutputStreamWriter(out)) {
			// create FileWriter object with file as parameter

			// create CSVWriter object filewriter object as parameter
			writer = new CSVWriter(streamWriter, ';', 
					CSVWriter.NO_QUOTE_CHARACTER, 
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
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
	
	public byte[] getBytesOf(Conteo conteo, ContestLog contestLog) {
		
		RelQsoConteo relQsoConteo;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CSVWriter writer = null;
		try (OutputStreamWriter streamWriter = new OutputStreamWriter(out)) {
			// create FileWriter object with file as parameter

			// create CSVWriter object filewriter object as parameter
			writer = new CSVWriter(streamWriter, ';', 
					CSVWriter.NO_QUOTE_CHARACTER, 
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
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
			writer = new CSVWriter(streamWriter, ';', 
					CSVWriter.NO_QUOTE_CHARACTER, 
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
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
		
		private void get
	}

}
