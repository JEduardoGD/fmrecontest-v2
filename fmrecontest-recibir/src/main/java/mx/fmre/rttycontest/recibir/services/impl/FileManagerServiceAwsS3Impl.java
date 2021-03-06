package mx.fmre.rttycontest.recibir.services.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.bs.util.FileUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.recibir.helper.EditionStaticHelper;
import mx.fmre.rttycontest.recibir.services.AWSS3Service;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;

@Slf4j
@Service("FileManagerServiceAwsS3")
public class FileManagerServiceAwsS3Impl implements IFileManagerService {
	
//	SampleInterface sample = appContext.getBean(service, SampleInterface.class);
	
	@Autowired
	private AWSS3Service s3Service;
	
	@Autowired
	private IEditionRepository editionRepository;

	@Value("${amazon.s3.bucketname}")
	private String bocketName;
	
	@Override
	public String saveFile(Email email, AttachedFileDTO fileDTO) {
		log.info("start saving file {}", fileDTO.getFilename());
		Edition edition = editionRepository.findById(email.getEdition().getId()).orElse(null);
		String filename = EditionStaticHelper.filename(email, edition, fileDTO);
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(fileDTO.getContenyType());
		metaData.setContentLength(fileDTO.getByteArray().length);
		PutObjectResult putObjectResult = s3Service.putObject(
				bocketName,
				filename,
				FileUtil.byteArrayToInputStream(fileDTO.getByteArray()), 
				metaData);
		log.info("ending saving with results: {}", putObjectResult);
		return filename;
	}

	@Override
	public AttachedFileDTO getFile(Email email, AttachedFileDTO fileDTO) throws FmreContestException {
		Edition edition = editionRepository.findById(email.getEdition().getId()).orElse(null);
		String filename = EditionStaticHelper.filename(email, edition, fileDTO);
		S3Object s3Object = s3Service.getObject(bocketName, filename);
		byte[] byteArray;
		try {
			byteArray = IOUtils.toByteArray(s3Object.getObjectContent());
			fileDTO.setByteArray(byteArray);
			return fileDTO;
		} catch (IOException e) {
			throw new FmreContestException(e.getLocalizedMessage());
		}
	}
}
