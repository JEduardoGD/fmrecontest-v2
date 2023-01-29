package mx.fmre.rttycontest.bs.parsers.rtty2020;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.location.exception.GridLocatorException;
import mx.fmre.rttycontest.bs.location.service.IGridLocatorService;
import mx.fmre.rttycontest.bs.parsers.IQsoParserService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.geo.dto.Location;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.State;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IStateRepository;

@Slf4j
@Service("qsoParserVhfUhfServiceImpl")
public class QsoParserVhfUhfServiceImpl implements IQsoParserService {
    
    @Autowired private IGridLocatorService   gridLocatorService;
    @Autowired private IStateRepository      stateRepository;
    @Autowired private IContestLogRepository contestLogRepository;
    
	private final String propertiesMap = "" + 
			"address=ADDRESS\n" + 
			"addressCity=ADDRESS-CITY\n" + 
			"addressCountry=ADDRESS-COUNTRY\n" + 
			"addressStateProvince=ADDRESS-STATE-PROVINCE\n" + 
			"callsign=CALLSIGN\n" + 
			"categoryBand=CATEGORY-BAND\n" + 
			"categoryMode=CATEGORY-MODE\n" + 
			"categoryOperator=CATEGORY-OPERATOR\n" + 
			"categoryPower=CATEGORY-POWER\n" + 
			"claimedScore=CLAIMED-SCORE\n" + 
			"club=CLUB\n" + 
			"contest=CONTEST\n" + 
			"createBy=CREATED-BY\n" + 
			"emailAddress=EMAIL\n" + 
			"name=NAME\n" + 
			"operators=OPERATORS\n" + 
			"soapbox=SOAPBOX\n" + 
			"startoflog=START-OF-LOG";

	@Override
	public ContestLog parse(Edition edition, List<String> stringList) throws FmreContestException {
		Map<String, String> m = getPropertiesMap(propertiesMap);

		ContestLog contestlog = new ContestLog();
		
		for (String l : stringList) {
			for (Entry<String, String> entry : m.entrySet()) {
				if (l.trim().startsWith(entry.getValue()))
					try {
						fill(contestlog, entry.getKey(), l.trim());
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						log.error(e.getLocalizedMessage());
					}
			}
		}

		List<ContestQso> listContest = new ArrayList<>();
		
		
		List<String> qsoLines = stringList
				.stream()
				.filter(s -> (s != null && s.toUpperCase().startsWith("QSO")))
				.collect(Collectors.toList());
		
		for (String l : qsoLines) {
			ContestQso contestQso = parseLine(l.trim());
			if (contestQso != null) {
				contestQso.setContestLog(contestlog);
				listContest.add(contestQso);
			}
		}
		contestlog.setContestqsos(listContest);
		
        if (       contestlog != null
                && contestlog.getOperators() != null
                && contestlog.getOperators().toLowerCase().endsWith("/m")) {
            List<ContestLog> contestlogGroup = contestLogRepository.findByEditionAndCallsign(edition, contestlog.getCallsign());
            contestlogGroup = contestlogGroup.stream().filter(log -> log.getGroup() != null).collect(Collectors.toList());
            Long group;
            if (contestlogGroup != null && !contestlogGroup.isEmpty()) {
                group = contestlogGroup.get(0).getGroup();
            } else {
                Long maxGroup = contestLogRepository.getMaxGroup();
                group = maxGroup == null ? 1l : contestLogRepository.getMaxGroup().longValue() + 1;
            }
            contestlog.setGroup(group);
        }
		
		return contestlog;
	}
	
	private ContestQso parseLine(String l) {
		SimpleDateFormat sdfQso = new SimpleDateFormat("yyyy-MM-dd HHmm");
		ContestQso qso = null;

		boolean matched = false;

		String freqS = null;
		String modeS = null;
		String dateS = null;
		String hourS = null;
		String callsignES = null;
		String gridLocator = null;
		String exchangeES = null;
		String callsignRS = null;
		String rstE = null;
		String rstR = null;

		Matcher m1 = p1.matcher(l);
        Matcher m2 = p2.matcher(l);

		String[] s = l.split("\\s+");

		if (m1.matches()) {
			matched = true;
			try {
				freqS = s[1];
				modeS = s[2];
				dateS = s[3];
				hourS = s[4];
				callsignES = s[5];
				rstE = s[6];
				callsignRS = s[7];
				rstR = s[8];
				gridLocator = s[9];
			} catch (IndexOutOfBoundsException e) {
				log.error(e.getLocalizedMessage());
			}
		}

        if (m2.matches()) {
            matched = true;
            try {
                freqS = s[1];
                modeS = s[2];
                dateS = s[3];
                hourS = s[4];
                callsignES = s[5];
                //String gridLocatorOrigin = s[6];
                callsignRS = s[7];
                gridLocator/*Destiny*/ = s[8];
            } catch (IndexOutOfBoundsException e) {
                log.error(e.getLocalizedMessage());
            }
        }
		
		if (matched) {
			Integer freq = new Integer(freqS);
			String mode = modeS;
			Date datetime = null;
			try {
				datetime = sdfQso.parse(dateS + " " + hourS);
			} catch (ParseException e) {
				log.error(e.getLocalizedMessage());
			}
			String callsignE = callsignES;
			String exchangeE = exchangeES;
			String callsignR = callsignRS;

			qso = new ContestQso();
			qso.setFrequency(freq);
			qso.setMode(mode);
			qso.setDatetime(datetime);
			qso.setCallsigne(callsignE);
			qso.setRste(rstE);
			qso.setExchangee(exchangeE);
			qso.setCallsignr(callsignR);
			qso.setRstr(rstR);
			qso.setExchanger(null);
			qso.setError(null);
			qso.setGridLocator(gridLocator);
			qso.setState(getStateOfGridLocator(gridLocator));
            
            
		} else {
			log.warn(String.format("Not parset %s", l));
		}
		
		return qso;
	}
	
	private State getStateOfGridLocator(String gridLocator) {
	    Location originLocation = null;
        if (gridLocator != null) {
            try {
                originLocation = gridLocatorService.getLocationOf(gridLocator);
            } catch (GridLocatorException e) {
                log.error(e.getLocalizedMessage());
            }
            if(originLocation == null) {
                return null;
            }
            String region = originLocation.getRegion();
            List<State> locations = stateRepository.findByGridLocatorEntity(region);
            if(locations!=null && !locations.isEmpty()) {
                return locations.get(0);
            }
        }
        return null;
	}

	private void fill(ContestLog contestlog, String property, String value)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] arr = value.split("\\:");
		if (arr == null || arr.length != 2)
			return;
		String v = arr[1] != null && !arr[1].equals("") ? arr[1].trim() : null;
		Class<?> c = contestlog.getClass();
		Method[] allMethods = c.getDeclaredMethods();
		String propertyName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length());
		for (Method m : allMethods) {
			String mname = m.getName();
			if (!mname.toLowerCase().equals(propertyName.toLowerCase())) {
				continue;
			}
			Type[] pType = m.getGenericParameterTypes();
			Type type = pType[0];
			m.setAccessible(true);
			// System.out.println(type.toString());
			switch (type.toString()) {
			case "class java.lang.String":
				m.invoke(contestlog, v);
				break;
			case "int":
				Integer vInteger = new Integer(v);
				if (v == null || v.equals("")) {
					m.invoke(contestlog, vInteger);
					break;
				}
				m.invoke(contestlog, vInteger.intValue());
				break;
			default:
			}
			break;
		}
	}

	private static Map<String, String> getPropertiesMap(String s) {
		Map<String, String> m = new HashMap<>();
		String[] lines = s.split("\\n");
		for (String line : lines) {
			String[] arr = line.split("\\=");
			m.put(arr[0], arr[1]);
		}
		return m;
	}
	

	private static final Pattern p1 = Pattern.compile(
			"^(QSO:)\\s+\\d+\\s+\\w+\\s+(\\d{4}-\\d{2}-\\d{2})\\s+\\d+\\s+(\\w|\\/)+\\s+(\\w|\\/)+\\s+(\\w|\\/)+\\s+(\\w|\\/)+\\s+(\\w|\\/)+\\s*");
	private static final Pattern p2 = Pattern.compile(
	        "^(QSO:)\\s+\\d+\\s+\\w+\\s+(\\d{4}-\\d{2}-\\d{2})\\s+\\d{4}\\s+(\\w|\\/)+\\s+(\\w|\\/)+\\s+(\\w|\\/)+\\s+(\\w|\\/)+\\s*$");
}
