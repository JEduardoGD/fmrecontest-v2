package mx.fmre.rttycontest.recibir.services;

//@Service
public class MailClient {

//    private JavaMailSender mailSender;
//    private MailContentBuilder mailContentBuilder;
//
//    @Autowired
//    public MailClient(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
//        this.mailSender = mailSender;
//        this.mailContentBuilder = mailContentBuilder;
//    }
//
//    public void prepareAndSend(String recipient, String message) {
//        MimeMessagePreparator messagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom("sample@dolszewski.com");
//            messageHelper.setTo(recipient);
//            messageHelper.setSubject("Sample mail subject");
//            String content = mailContentBuilder.build(message);
//            messageHelper.setText(content, true);
//        };
//        try {
//            mailSender.send(messagePreparator);
//        } catch (MailException e) {
//        }
//    }

}