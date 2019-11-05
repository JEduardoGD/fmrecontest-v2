package mx.fmre.rttycontest.recibir.services.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.recibir.dto.AttachedFileDTO;
import mx.fmre.rttycontest.recibir.helper.EditionStaticHelper;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;

@Slf4j
@Service("FileManagerServiceLocal")
public class FileManagerServiceLocalImpl implements IFileManagerService {

	@Autowired
	private IEditionRepository editionRepository;

	@Override
	public String saveFile(Email email, AttachedFileDTO fileDTO) throws FmreContestException {
		log.info("start saving file {}", fileDTO.getFilename());
		Edition edition = editionRepository.findById(email.getEdition().getId()).orElse(null);
		String filename = EditionStaticHelper.filename(email, edition, fileDTO);
		
		String putObjectResult;
		try {
			Path path = Paths.get(filename);
			Path parentPath = path.getParent();
			if (!parentPath.toFile().exists()) {
				Files.createDirectories(parentPath);
			}
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			throw new FmreContestException(e.getLocalizedMessage());
		}
		
		try (OutputStream os = new FileOutputStream(filename)) {
			Path path = Paths.get(filename);
			Path parentPath = path.getParent();
			if (!parentPath.toFile().exists()) {
				Files.createDirectories(parentPath);
			}
			os.write(fileDTO.getByteArray());
			putObjectResult = filename;
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			throw new FmreContestException(e.getLocalizedMessage());
		}

		log.info("ending saving with results: {}", putObjectResult);
		return filename;

	}

	@Override
	public void saveFile(AttachedFileDTO fileDTO) {
		// TODO Auto-generated method stub

	}

}
