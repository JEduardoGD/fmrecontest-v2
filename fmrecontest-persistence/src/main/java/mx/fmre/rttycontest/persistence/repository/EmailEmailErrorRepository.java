package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.fmre.rttycontest.persistence.model.EmailEmailError;
import mx.fmre.rttycontest.persistence.model.EmailEmailErrorPk;

@Repository
public interface EmailEmailErrorRepository extends JpaRepository<EmailEmailError, EmailEmailErrorPk> {

}
