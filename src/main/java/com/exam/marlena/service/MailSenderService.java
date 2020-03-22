package com.exam.marlena.service;

import static com.sendgrid.Method.POST;

import com.exam.marlena.domain.Message;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

  private static final String ENDPOINT = "mail/send";

  @Value("${sender.mail}")
  private String senderMail;

  @Value("${sendgrid.api.key}")
  private String apiKey;

  public String send(Message message) {
    Email from = new Email(senderMail);
    Email to = new Email(message.getTo());
    Content content = new Content("text/plain", message.getContent());
    Mail mail = new Mail(from, message.getSubject(), to, content);

    SendGrid sg = new com.sendgrid.SendGrid(apiKey);
    Request request = new Request();
    try {
      request.setMethod(POST);
      request.setEndpoint(ENDPOINT);
      request.setBody(mail.build());
      Response response = sg.api(request);
      return "Message sent to " + message.getTo() + " ,status code: " + response.getStatusCode();
    } catch (IOException ex) {
      return "There was a problem while sending email message to " + message.getTo() + "\n" + ex;
    }
  }
}

