package ru.dmitrii.solid;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {

    /**
     * Подготовка и отправка сообщения
     * @param departmentId String
     * @param dateFrom LocalDate
     * @param dateTo LocalDate
     * @param recipients String
     */
    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        SqlHandler sqlHandler = new SqlHandler();
        try {
            PreparedStatement ps = sqlHandler.getPreparedStatement(departmentId, dateFrom, dateTo);
            // execute query and get the results
            ResultSet results = ps.executeQuery();
            StringBuilder resultingHtml = sqlHandler.getStringBuilder(results);
            // now when the report is built we need to send it to the recipients list
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            MimeMessage message = getMimeMessage(recipients, resultingHtml, mailSender);
            // send the message
            mailSender.send(message);
        } catch (SQLException | MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * construct the message for google
     * @param recipients String
     * @param resultingHtml StringBuilder
     * @param mailSender JavaMailSenderImpl
     * @return MimeMessage
     * @throws MessagingException MessagingException
     */
    private MimeMessage getMimeMessage(String recipients, StringBuilder resultingHtml, JavaMailSenderImpl mailSender)
            throws MessagingException {
        mailSender.setHost("mail.google.com");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipients);
        // setting message text, last parameter 'true' says that it is HTML format
        helper.setText(resultingHtml.toString(), true);
        helper.setSubject("Monthly department salary report");
        return message;
    }
}
