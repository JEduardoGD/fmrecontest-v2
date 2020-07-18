package mx.fmre.rttycontest.bs.util;

import java.util.List;
import java.util.stream.Collectors;

import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.persistence.model.AttachedFile;

public abstract class AttachedFileUtil {
	public static List<AttachedFileDTO> map(List<AttachedFile> attachedFiles) {
		return attachedFiles.stream().map(af -> {
			return AttachedFileUtil.map(af);
		}).collect(Collectors.toList());
	}

	public static AttachedFileDTO map(AttachedFile attachedFile) {
		AttachedFileDTO a = new AttachedFileDTO();
		a.setId(attachedFile.getId());
		a.setFilename(attachedFile.getFilename());
		a.setContentType(attachedFile.getContentType());
		a.setLenght(attachedFile.getLenght());
		a.setPath(attachedFile.getPath());
		return a;
	}
}