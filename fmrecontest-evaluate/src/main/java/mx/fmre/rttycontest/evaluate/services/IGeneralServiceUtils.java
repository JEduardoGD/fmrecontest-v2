package mx.fmre.rttycontest.evaluate.services;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;

public interface IGeneralServiceUtils {

    List<Email> filter(List<Email> listEmail, Edition edition);

}
