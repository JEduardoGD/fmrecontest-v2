package mx.fmre.rttycontest.recibir.business.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.bs.parsers.IQsoParserService;
import mx.fmre.rttycontest.bs.util.AttachedFileUtil;
import mx.fmre.rttycontest.bs.util.FileUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IAttachedFileRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.recibir.business.IParserEmail;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;

@Slf4j
@Service
public class ParserEmailServiceRtty2020Impl implements IParserEmail {
	
	@Autowired private IAttachedFileRepository attachedFileRepository;
	@Autowired private ApplicationContext appContext;
	@Autowired private IEditionRepository editionRepository;
	
	@Value("${file.manager.impl}")
	private String fileManagerImpl;
	
	private final String logFileNamePattern = "^[\\w,\\s-]+\\.(([tT][xX][tT])|([lL][oO][gG])|([cC][bB][rR]))$";

	@Override
	public List<AttachedFile> identifyLogFile(Email email) {
		Pattern pattern = Pattern.compile(logFileNamePattern);
		List<AttachedFile> attachedFiles = attachedFileRepository.findByEmail(email);
		return attachedFiles.stream().filter(attachedFile -> {
			 Matcher matcher = pattern.matcher(attachedFile.getFilename());
			 return matcher.matches();
		}).collect(Collectors.toList());
	}

	@Override
	public ContestLog parse(Email email) {
		List<AttachedFile> attachedFiles = attachedFileRepository.findByEmail(email);
		AttachedFile attachedLogFile = attachedFiles
		.stream()
		.filter(attachedFile -> attachedFile.isLogFile())
		.findFirst()
		.orElse(null);
		try {
			return this.parse(email, attachedLogFile);
		} catch (FmreContestException e) {
			log.error(e.getLocalizedMessage());
		}
		return null;
	}
	
	private ContestLog parse(Email email, AttachedFile attachedLogFile) throws FmreContestException {
		IFileManagerService fileManagerService = appContext.getBean(fileManagerImpl, IFileManagerService.class);
		Edition edition = editionRepository.findById(email.getEdition().getId()).orElse(null);
		IQsoParserService qsoParser = appContext.getBean(edition.getQsoParserImpl(), IQsoParserService.class);
		
		AttachedFileDTO attachedFileDTOMapped = AttachedFileUtil.map(attachedLogFile);
		AttachedFileDTO attachedFileDTOWithByteArray = fileManagerService.getFile(email, attachedFileDTOMapped);
		InputStream is = FileUtil.byteArrayToInputStream(attachedFileDTOWithByteArray.getByteArray());
		try {
			String oneString = FileUtil.inputStreamToString(is);
			String[] stringArray = oneString.split("\\n");
			ContestLog contestLog = qsoParser.parse(Arrays.asList(stringArray));
			contestLog.setEmail(email);
			return contestLog;
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			throw new FmreContestException(e.getLocalizedMessage());
		}
	}
}
