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

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.parsers.IQsoParserService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;

@Slf4j
@Service("qsoParserRtty2020ServiceImpl")
public class QsoParserRtty2020ServiceImpl implements IQsoParserService {
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
		String rstES = null;
		String exchangeES = null;
		String callsignRS = null;
		String rstRS = null;
		String exchangeRS = null;

		Matcher m1 = p1.matcher(l);
		Matcher m2 = p2.matcher(l);
		Matcher m3 = p3.matcher(l);
		Matcher m4 = p4.matcher(l);

		String[] s = l.split("\\s+");

		if (m1.matches()) {
			matched = true;
			try {
				freqS = s[1];
				modeS = s[2];
				dateS = s[3];
				hourS = s[4];
				callsignES = s[5];
				rstES = s[6];
				exchangeES = s[7];
				callsignRS = s[8];
				rstRS = s[9];
				exchangeRS = s[10];
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
				String[] arr2 = s[6].split("\\-");
				rstES = arr2[0];
				exchangeES = arr2[1];
				callsignRS = s[7];
				rstRS = s[8];
				exchangeRS = s[9];
			} catch (IndexOutOfBoundsException e) {
				log.error(e.getLocalizedMessage());
			}
		}

		if (m3.matches()) {
			matched = true;
			try {
				freqS = s[1];
				modeS = s[2];
				dateS = s[3];
				hourS = s[4];
				callsignES = s[5];
				rstES = s[6];
				exchangeES = s[7];
				callsignRS = s[8];
				rstRS = s[9];
				exchangeRS = s[10];
			} catch (IndexOutOfBoundsException e) {
				log.error(e.getLocalizedMessage());
			}
		}

		if (m4.matches()) {
			matched = true;
			try {
				freqS = s[1];
				modeS = s[2];
				dateS = s[3];
				hourS = s[7];
				callsignES = s[4];
				rstES = s[5];
				exchangeES = s[6];
				callsignRS = s[8];
				rstRS = s[9];
				exchangeRS = s[10];
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
			String rstE = rstES;
			String exchangeE = exchangeES;
			String callsignR = callsignRS;
			String rstR = rstRS;
			String exchangeR = exchangeRS;

			qso = new ContestQso();
			qso.setFrequency(freq);
			qso.setMode(mode);
			qso.setDatetime(datetime);
			qso.setCallsigne(callsignE);
			qso.setRste(rstE);
			qso.setExchangee(exchangeE);
			qso.setCallsignr(callsignR);
			qso.setRstr(rstR);
			qso.setExchanger(exchangeR);
			qso.setError(null);
		} else {
			log.warn(String.format("Not parset %s", l));
		}
		
		return qso;
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

	// ^(QSO:)\s+\d+\s+\w+\s+(\d{4}-\d{2}-\d{2})\s+\d+\s+(\w|\/)+\s+\d+\s+\w+\s+(\w|\/)+\s+\d+\s+(\d|\w)+\s*$
	// QSO: 14091 RY 2018-02-04 1751 VE2BVV 599 0132 W4UK 599 0193
	// QSO: 14093 RY 2018-02-04 1752 VE2BVV 599 0133 XE2YWB 599 ZAC
	// QSO: 14082 RY 2018-02-04 2049 VE2BVV 599 0183 K8RGI/4 599 0038

	// ^(QSO:)\s+\d+\s+\w+\s+(\d{4}-\d{2}-\d{2})\s+\d+\s+(\w|\/)+\s+\d+-\w+\s+\w+\s+(\w|\/)+\s+(\d|\w)+$

	// ^(QSO:)\s+\d+\s+\w+\s+(\d{4}-\d{2}-\d{2})\s+\d+\s+(\w|\/)+\s+\d+\s+\w+\s+(\w|\/)+\s+\d+\s+(\d|\w)+\s*(0)$
	// QSO: 14081 RY 2018-02-03 1532 UT4RZ 599 005 ON8HW 599 AGU 0
	// QSO: 14081 RY 2018-02-03 1531 UT4RZ 599 004 G4SJX 599 022 0

	// ^(QSO:)\s+\d+\s+\w+\s+(\d{4}-\d{2}-\d{2})\s+(\w|\/)+\s+\d+\s+\d+\s+\w+\s+(\w|\/)+\s+\d+\s+(\d|\w)+\s*$
	// QSO: 14071 RY 2018-02-03 G3SNU 599 001 1250 S57YK 599 000

	private static final Pattern p1 = Pattern.compile(
			"^(QSO:)\\s+\\d+\\s+\\w+\\s+(\\d{4}-\\d{2}-\\d{2})\\s+\\d+\\s+(\\w|\\/)+\\s+\\d+\\s+\\w+\\s+(\\w|\\/)+\\s+\\d+\\s+(\\d|\\w)+\\s*$");
	private static final Pattern p2 = Pattern.compile(
			"^(QSO:)\\s+\\d+\\s+\\w+\\s+(\\d{4}-\\d{2}-\\d{2})\\s+\\d+\\s+(\\w|\\/)+\\s+\\d+-\\w+\\s+\\w+\\s+(\\w|\\/)+\\s+(\\d|\\w)+$");
	private static final Pattern p3 = Pattern.compile(
			"^(QSO:)\\s+\\d+\\s+\\w+\\s+(\\d{4}-\\d{2}-\\d{2})\\s+\\d+\\s+(\\w|\\/)+\\s+\\d+\\s+\\w+\\s+(\\w|\\/)+\\s+\\d+\\s+(\\d|\\w)+\\s*(0)$");
	private static final Pattern p4 = Pattern.compile(
			"^(QSO:)\\s+\\d+\\s+\\w+\\s+(\\d{4}-\\d{2}-\\d{2})\\s+(\\w|\\/)+\\s+\\d+\\s+\\d+\\s+\\w+\\s+(\\w|\\/)+\\s+\\d+\\s+(\\d|\\w)+\\s*$");
}
