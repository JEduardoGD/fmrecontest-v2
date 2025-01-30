package mx.fmre.rttycontest.recibir.services;

import mx.fmre.rttycontest.recibir.dto.RemoteLog;
import mx.fmre.rttycontest.recibir.exception.ExternalConnectionException;

public interface ExternalConnectionService {

	RemoteLog[] getAllByYear(int year) throws ExternalConnectionException;

	Long getNextId() throws ExternalConnectionException;

	RemoteLog save(RemoteLog remoteLog) throws ExternalConnectionException;
}
