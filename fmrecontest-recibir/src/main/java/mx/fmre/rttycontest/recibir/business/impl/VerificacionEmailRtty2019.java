package mx.fmre.rttycontest.recibir.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IAttachedFileRepository;
import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.recibir.business.IVerificacionEmail;

@Service("verificacionEmailRtty2019")
public class VerificacionEmailRtty2019 implements IVerificacionEmail {

	@Autowired
	private IEditionRepository editionRepository;
	@Autowired
	private IAttachedFileRepository attachedFileRepository;
	@Autowired
	private ICatEmailErrorRepository catEmailErrorRepository;

	private final String EMAIL_WITHOUT_SUBJECT = "EMAIL_WITHOUT_SUBJECT";
	private final String EMAIL_WITHOUT_ATTACHED_FILES = "EMAIL_WITHOUT_ATTACHED_FILES";
//	private final String SUBJECT_NOT_EQUALS_SENDER = "SUBJECT_NOT_EQUALS_SENDER";

	@Override
	public List<CatEmailError> verify(Email email) throws FmreContestException {
		Integer editionId = email.getEdition().getId();
		Edition edition = editionRepository.findById(editionId).orElse(null);
		List<AttachedFile> attachedFiles = attachedFileRepository.findByEmail(email);

		List<CatEmailError> listCatEmailError = new ArrayList<>();

		CatEmailError x;

		/* EMAIL_WITHOUT_SUBJECT */
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, EMAIL_WITHOUT_SUBJECT);
		if (x != null) {
			if (this.verify_EMAIL_WITHOUT_SUBJECT(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException(
					"The test \"" + EMAIL_WITHOUT_SUBJECT + "\" is not found for editon with ID " + editionId);

		/* SUBJECT_NOT_EQUALS_SENDER */
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, EMAIL_WITHOUT_ATTACHED_FILES);
		if (x != null) {
			if (this.verify_EMAIL_WITHOUT_ATTACHED_FILES(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException(
					"The test \"" + EMAIL_WITHOUT_ATTACHED_FILES + "\" is not found for editon with ID " + editionId);

//		/*EMAIL_WITHOUT_ATTACHED_FILES*/
//		x = catEmailErrorRepository.findByEditionAndDescription(edition, SUBJECT_NOT_EQUALS_SENDER);
//		if(x != null) {
//			if(this.verify_SUBJECT_NOT_EQUALS_SENDER(email, edition, attachedFiles)) {
//				listCatEmailError.add(x);
//			}
//		} else
//			throw new FmreContestException("The test \"" + SUBJECT_NOT_EQUALS_SENDER + "\" is not found for editon with ID " + editionId);

		return listCatEmailError;
	}

	private boolean verify_EMAIL_WITHOUT_SUBJECT(Email email, Edition edition, List<AttachedFile> attachedFiles) {
		return (email.getSubject() == null || "".equals(email.getSubject()));
	}

	private boolean verify_EMAIL_WITHOUT_ATTACHED_FILES(Email email, Edition edition,
			List<AttachedFile> attachedFiles) {
		return (attachedFiles != null && attachedFiles.size() > 0);
	}

//	private boolean verify_SUBJECT_NOT_EQUALS_SENDER(Email email, Edition edition, List<AttachedFile> attachedFiles) {
//		return (email != null && email.getSubject() != null && email.getSubject() );
//	}
}
