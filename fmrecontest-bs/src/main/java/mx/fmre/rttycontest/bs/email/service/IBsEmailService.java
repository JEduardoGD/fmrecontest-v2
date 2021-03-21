package mx.fmre.rttycontest.bs.email.service;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.Email;

public interface IBsEmailService {

    List<Email> getValidEmailsOfEdition(int editionId);

}
