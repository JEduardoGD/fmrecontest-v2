package mx.fmre.rttycontest.recibir.services;

import org.springframework.web.client.HttpClientErrorException;

import mx.fmre.rttycontest.recibir.dto.RemoteLog;

public interface ExternalConnectionService {

	RemoteLog[] getAllByYear(int year) throws HttpClientErrorException;

	Long getNextId() throws HttpClientErrorException;

	RemoteLog save(RemoteLog remoteLog) throws HttpClientErrorException;
}
