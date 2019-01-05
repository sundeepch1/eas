package employeeservices;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmail {

	final String senderEmailID = "employeeattendancesystem365@gmail.com";
	final String senderPassword = "muna123.";
	final String emailSMTPserver = "smtp.gmail.com";
	final String emailServerPort = "465";

	public String sendEmailToEmployee(String receiverEmailID, String employeeName,String resetPasswordId) {

		String result = "";

		// Subject
		String emailSubject = "Reset your password";
		// Body
		String emailBody = "Dear " + employeeName +",\n\nReset your password on clicking the given link\n\n"
							+ "http://localhost:9000/#!/resetpassword/"+resetPasswordId+ "\n\n"
							+ "All the right belong to Employee Attendance System\n" 
							+ "Have a nice day";

		Properties props = new Properties();
		props.put("mail.smtp.user", senderEmailID);
		props.put("mail.smtp.host", emailSMTPserver);
		props.put("mail.smtp.port", emailServerPort);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", emailServerPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		//SecurityManager security = System.getSecurityManager();
		try {
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			MimeMessage msg = new MimeMessage(session);
			msg.setText(emailBody);
			msg.setSubject(emailSubject);
			msg.setFrom(new InternetAddress(senderEmailID));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID));
			Transport.send(msg);
			result = "Password reset link successfully send";
		}

		catch (Exception mex) {
			mex.printStackTrace();
		}
		return result;
	}

	public class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(senderEmailID, senderPassword);
		}
	}
}
