package mx.fmre.rttycontest.recibir.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import mx.fmre.rttycontest.recibir.dto.EmailDataDTO;
import mx.fmre.rttycontest.recibir.dto.OthersLogs;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;
    
    private DateFormat df_es_MX = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm:ss",  new Locale("es", "MX"));
    private DateFormat df_en = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss",  Locale.ENGLISH);

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(EmailDataDTO emailDataDTO) {
        Context context = new Context();
        context.setVariable("subject", emailDataDTO.getSubject());
        context.setVariable("emailSubject", emailDataDTO.getEmailSubject());
        context.setVariable("errors", emailDataDTO.getErrors());
        context.setVariable("dateOfSendEs", df_es_MX.format(emailDataDTO.getDateOfSend()));
        context.setVariable("dateOfSendEn", df_en.format(emailDataDTO.getDateOfSend()));
        context.setVariable("callsign", emailDataDTO.getCallsign());
        context.setVariable("recipientFromName", emailDataDTO.getFromName());
        context.setVariable("recipientFromAddress", emailDataDTO.getFromAddress());
        context.setVariable("noQsos", emailDataDTO.getNoQsos());
        context.setVariable("listOthersLogs", this.parseOthersLogs(emailDataDTO.getOthersLogs()));
        context.setVariable("debugData", emailDataDTO.getDebugData());
        return templateEngine.process(emailDataDTO.getTemplate(), context);
    }
    
    private List<OthersLogs> parseOthersLogs(List<OthersLogs> listOthersLogs) {
    	return listOthersLogs.stream().map(ol -> {
    		ol.setSDateOfSend(df_es_MX.format(ol.getDateOfSend()));
    		return ol;
    	}).collect(Collectors.toList());
    }

}