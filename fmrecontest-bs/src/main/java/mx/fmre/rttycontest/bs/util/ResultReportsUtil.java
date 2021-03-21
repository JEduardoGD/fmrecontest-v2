package mx.fmre.rttycontest.bs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;

public final class ResultReportsUtil {
	public static List<RelConteoContestLog> filterHighPowerWorld(
			List<RelConteoContestLog> listRelConteoContestLog,
			IContestLogRepository contestLogRepository) {

		List<RelConteoContestLog> highPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return (StaticValues.HIGH_POWER.equalsIgnoreCase(contestLog.getCategoryPower()));
					})
				.collect(Collectors.toList());
		
		return highPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
	}
	
	public static List<RelConteoContestLog> filterLowPowerWorld(
			List<RelConteoContestLog> listRelConteoContestLog,
			IContestLogRepository contestLogRepository) {
		List<RelConteoContestLog> lowPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return (null == contestLog.getCategoryPower() || StaticValues.LOW_POWER.equalsIgnoreCase(contestLog.getCategoryPower()));
					})
				.collect(Collectors.toList());
		
		return lowPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
	}
	
	public static List<RelConteoContestLog> filterHighPowerMexico(
			List<RelConteoContestLog> listRelConteoContestLog,
			IContestLogRepository contestLogRepository,
			DxccEntity mexicoDxccEntity) {
		List<RelConteoContestLog> highPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return (StaticValues.HIGH_POWER.equalsIgnoreCase(contestLog.getCategoryPower()) &&
							contestLog.getDxccEntity().getEntityCode().equals(mexicoDxccEntity.getEntityCode()));
					})
				.collect(Collectors.toList());
		
		return highPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
	}
	
	public static List<RelConteoContestLog> filterLowPowerMexico(
			List<RelConteoContestLog> listRelConteoContestLog,
			IContestLogRepository contestLogRepository,
			DxccEntity mexicoDxccEntity) {

		List<RelConteoContestLog> lowPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					Long contestLogId = rcc.getContestLog().getId();
					ContestLog contestLog = contestLogRepository.findById(contestLogId).orElse(null);
					return ((null == contestLog.getCategoryPower() || StaticValues.LOW_POWER.equalsIgnoreCase(contestLog.getCategoryPower())) &&
							contestLog.getDxccEntity().getEntityCode().equals(mexicoDxccEntity.getEntityCode()));
					})
				.collect(Collectors.toList());
		
		return lowPowerListRelConteoContestLog
				.stream()
				.sorted((o1,o2)->  {
					if(o1.getTotalPoints() < o2.getTotalPoints())
						return 1;
					if(o1.getTotalPoints() > o2.getTotalPoints())
						return -1;
					return 0;
				})
				.collect(Collectors.toList());
	}
	
	public static List<RelConteoContestLog> filterLowPowerByCountry(
			List<RelConteoContestLog> listRelConteoContestLog,
			IContestLogRepository contestLogRepository,
			DxccEntity mexicoDxccEntity) {

		
		List<ContestLog> contestLogList = listRelConteoContestLog
				.stream()
				.map(rcc -> contestLogRepository.findById(rcc.getContestLog().getId()).orElse(null))
				.collect(Collectors.toList());

		return listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					long contestLogId = rcc.getContestLog().getId().longValue();
					ContestLog contestLog = contestLogList
							.stream()
							.filter(cl -> cl.getId().longValue() == contestLogId)
							.findFirst()
							.orElse(null);
					return (null == contestLog.getCategoryPower() || StaticValues.LOW_POWER.equalsIgnoreCase(contestLog.getCategoryPower()));
				})
				.collect(Collectors.toList());
	}
	
	public static List<RelConteoContestLog> filterHighPowerByCountry(
			List<RelConteoContestLog> listRelConteoContestLog,
			IContestLogRepository contestLogRepository,
			DxccEntity mexicoDxccEntity) {
		
		List<ContestLog> contestLogList = listRelConteoContestLog
				.stream()
				.map(rcc -> contestLogRepository.findById(rcc.getContestLog().getId()).orElse(null))
				.collect(Collectors.toList());
		
        List<Long> repeatedDxccEntityCodes = contestLogList.stream()
                .map(ContestLog::getDxccEntity)
                .map(DxccEntity::getEntityCode)
                .collect(Collectors.toList());

        List<Long> dxccEntityCodes = new ArrayList<>();
        for (Long r : repeatedDxccEntityCodes) {
            if (!dxccEntityCodes.contains(r)) {
                dxccEntityCodes.add(r);
            }
        }
		
		//filter low power
		List<RelConteoContestLog> highPowerListRelConteoContestLog = listRelConteoContestLog
				.stream()
				.filter(rcc -> {
					long contestLogId = rcc.getContestLog().getId().longValue();
					ContestLog contestLog = contestLogList
							.stream()
							.filter(cl -> cl.getId().longValue() == contestLogId)
							.findFirst()
							.orElse(null);
					return (StaticValues.HIGH_POWER.equalsIgnoreCase(contestLog.getCategoryPower()));
				})
				.collect(Collectors.toList());
		
		List<RelConteoContestLog> ordered = new ArrayList<>();
		
		for (Long dxccEntity : dxccEntityCodes) {
			List<RelConteoContestLog> rccByDxccEntity = highPowerListRelConteoContestLog
					.stream()
					.filter(rcc -> {
						long contestLogId = rcc.getContestLog().getId().longValue();
						ContestLog contestLog = contestLogList
								.stream()
								.filter(cl -> cl.getId().longValue() == contestLogId)
								.findFirst()
								.orElse(null);
						return contestLog.getDxccEntity().getEntityCode().equals(dxccEntity);
					})
					.collect(Collectors.toList());
					
			if(rccByDxccEntity.isEmpty())
				continue;

			
			rccByDxccEntity = rccByDxccEntity
					.stream()
					.sorted((o1,o2)->  {
						if(o1.getTotalPoints() < o2.getTotalPoints())
							return 1;
						if(o1.getTotalPoints() > o2.getTotalPoints())
							return -1;
						return 0;
					})
					.collect(Collectors.toList());
			
			ordered.addAll(rccByDxccEntity);
			

			/*
			int place = 0;
			long lastPoints = -1;
			for (RelConteoContestLog q : rccByDxccEntity) {
				place = lastPoints == q.getTotalPoints() ? place : place + 1;
				
				Long contestLogId = q.getContestLog().getId();
				ContestLog contestLog = contestLogRepository
						.findById(contestLogId)
						.orElse(null);
				String[] content = {
						contestLog.getId() + "",
						contestLog.getCallsign(),
						contestLog.getDxccEntity().getId() + "",
						contestLog.getAddressStateProvince() == null ? "" : contestLog.getAddressStateProvince(),
						contestLog.getCategoryPower(),
						q.getTotalPoints() + "",
						place + ""};

				listStringsContent.add(content);
				
				lastPoints = q.getTotalPoints();
			}
				*/
		}
		
		return ordered;
	}
}
