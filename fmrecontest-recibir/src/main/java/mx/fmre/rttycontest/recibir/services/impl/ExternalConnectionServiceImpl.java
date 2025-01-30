package mx.fmre.rttycontest.recibir.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import mx.fmre.rttycontest.recibir.dto.RemoteLog;
import mx.fmre.rttycontest.recibir.exception.ExternalConnectionException;
import mx.fmre.rttycontest.recibir.services.ExternalConnectionService;

@Service
public class ExternalConnectionServiceImpl implements ExternalConnectionService {
	private static final String GET_ALL_BY_YEAR = "/api/logs/getall/byyear/%s";
	private static final String SAVE = "/api/logs";
	private static final String GET_NEXT_ID = "/api/logs/nextId";
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public RemoteLog[] getAllByYear(int year) throws ExternalConnectionException {
		// 1. getting by all saved logs
		try {
			return restTemplate.getForObject(String.format(GET_ALL_BY_YEAR, year), RemoteLog[].class);
		} catch (ResourceAccessException e) {
			throw new ExternalConnectionException(e);
		}
	}

	@Override
	public Long getNextId() throws ExternalConnectionException {
		// 1. getting by all saved logs
		try {
			return restTemplate.getForObject(GET_NEXT_ID, Long.class);
		} catch (ResourceAccessException e) {
			throw new ExternalConnectionException(e);
		}
	}

	@Override
	public RemoteLog save(RemoteLog remoteLog) throws ExternalConnectionException {
		// 1. getting by all saved logs
		try {
			return restTemplate.postForObject(SAVE, remoteLog, RemoteLog.class);
		} catch (ResourceAccessException e) {
			throw new ExternalConnectionException(e);
		}
	}

}
