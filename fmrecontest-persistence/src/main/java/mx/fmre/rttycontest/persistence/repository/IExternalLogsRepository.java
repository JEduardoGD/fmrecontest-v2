package mx.fmre.rttycontest.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.fmre.rttycontest.persistence.model.ExternalLogs;
import mx.fmre.rttycontest.persistence.model.ExternalLogsId;

public interface IExternalLogsRepository extends JpaRepository<ExternalLogs, ExternalLogsId>{
}
