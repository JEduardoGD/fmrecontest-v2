package mx.fmre.rttycontest.recibir.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.recibir.dto.AttachedFileDTO;
import mx.fmre.rttycontest.recibir.helper.FileUtil;
import mx.fmre.rttycontest.recibir.services.AWSS3Service;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;

@Service
public class FileManagerServiceImpl implements IFileManagerService {
	
	@Autowired
	private AWSS3Service s3Service;
	
	@Autowired
	private IEditionRepository editionRepository;

	@Value("${amazon.s3.bucketname}")
	private String bocketName;

	@Override
	public String saveFile(Email email, AttachedFileDTO fileDTO) {
		Edition edition = editionRepository.findById(email.getEdition().getId()).orElse(null);
		String filename = String.format("contest_%d/edition_%d/emailcount_%d/%s", 
				edition.getContest().getId(),
				edition.getId(),
				email.getEmailCount(),
				fileDTO.getFilename());
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(fileDTO.getContenyType());
		metaData.setContentLength(fileDTO.getByteArray().length);
		@SuppressWarnings("unused")
		PutObjectResult putObjectResult = s3Service.putObject(
				bocketName,
				filename,
				FileUtil.byteArrayToInputStream(fileDTO.getByteArray()), 
				metaData);
		return filename;
	}

	@Override
	public void saveFile(AttachedFileDTO fileDTO) {
	}

}
