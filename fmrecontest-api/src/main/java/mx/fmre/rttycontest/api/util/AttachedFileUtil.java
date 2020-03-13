package mx.fmre.rttycontest.api.util;

import java.util.List;
import java.util.stream.Collectors;

import mx.fmre.rttycontest.api.dto.AttachedFileDTO;
import mx.fmre.rttycontest.persistence.model.AttachedFile;

public class AttachedFileUtil {
	public static List<AttachedFileDTO> map(List<AttachedFile> attachedFiles){
		return attachedFiles.stream().map(x -> {
			AttachedFileDTO attachedFileDTO = new AttachedFileDTO();
			attachedFileDTO.setId(x.getId());
			attachedFileDTO.setFilename(x.getFilename());
			attachedFileDTO.setContentType(x.getContentType());
			attachedFileDTO.setLenght(x.getLenght());
			attachedFileDTO.setPath(x.getPath());
			return attachedFileDTO;
		}).collect(Collectors.toList());
	}
}
 