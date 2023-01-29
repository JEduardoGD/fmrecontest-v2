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
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.recibir.business.IVerificacionEmail;
import mx.fmre.rttycontest.recibir.business.base.BaseVerificationsService;

@Service("verificacionEmailVhfUhf2022Impl")
public class VerificacionEmailVhfUhf2022Impl extends BaseVerificationsService implements IVerificacionEmail {

    @Autowired private IEditionRepository editionRepository;
    @Autowired private IAttachedFileRepository attachedFileRepository;
    @Autowired private ICatEmailErrorRepository catEmailErrorRepository;
    @Autowired private IContestLogRepository contestLogRepository;

    private final String EMAIL_WITHOUT_SUBJECT = "EMAIL_WITHOUT_SUBJECT";
    private final String EMAIL_WITHOUT_ATTACHED_FILES = "EMAIL_WITHOUT_ATTACHED_FILES";
    private final String EMAIL_WITHOUT_CONTSTLOG = "EMAIL_WITHOUT_CONTSTLOG";

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
            if (verify_EMAIL_WITHOUT_SUBJECT(email, edition, attachedFiles)) {
                listCatEmailError.add(x);
            }
        } else
            throw new FmreContestException(
                    "The test \"" + EMAIL_WITHOUT_SUBJECT + "\" is not found for editon with ID " + editionId);

        /* EMAIL_WITHOUT_ATTACHED_FILES */
        x = catEmailErrorRepository.findByEditionAndDescripcion(edition, EMAIL_WITHOUT_ATTACHED_FILES);
        if (x != null) {
            if (this.verify_EMAIL_WITHOUT_ATTACHED_FILES(email, edition, attachedFiles)) {
                listCatEmailError.add(x);
            }
        } else
            throw new FmreContestException(
                    "The test \"" + EMAIL_WITHOUT_ATTACHED_FILES + "\" is not found for editon with ID " + editionId);

        /*EMAIL_WITHOUT_CONTSTLOG*/
        x = catEmailErrorRepository.findByEditionAndDescripcion(edition, EMAIL_WITHOUT_CONTSTLOG);
        if(x != null) {
            if(this.verify_EMAIL_WITHOUT_CONTSTLOG(email, edition, attachedFiles, contestLogRepository)) {
                listCatEmailError.add(x);
            }
        } else
            throw new FmreContestException("The test \"" + EMAIL_WITHOUT_CONTSTLOG + "\" is not found for editon with ID " + editionId);

        return listCatEmailError;
    }
}
