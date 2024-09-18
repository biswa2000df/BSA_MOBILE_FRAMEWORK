package com.mahindra.mobile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class MailSend {

	public static String Sr_No;
	public static String Process;
	public static String HOST;
	public static String Password;
	public static String MAIL_TO;
	public static String MAIL_CC;
	public static String SUBJECT;
	public static String BODY_MESSAGE;

	public static void mailSend() throws Exception {

		String MainControler = ConnectToMainController.mainControllerFilePath; // to get the maincontroller file path

		Fillo fillo = new Fillo();
		Connection conn = fillo.getConnection(MainControler);

		String query = "SELECT * FROM MAIL_SEND";
		String queryForProcessName = "SELECT * FROM MAIL_SEND Where RunStatus='Y' and Process='"
				+ ConnectToMainController.ApplicationName + "'";
		Recordset recordset = null;

		try {
			recordset = conn.executeQuery(query);
			if (recordset != null) {
//						System.out.println("MAIL_SEND sheet are present...");

				List<String> actualColumnName = recordset.getFieldNames();
				List<String> exceptedColumnName = new ArrayList<>();
				exceptedColumnName.addAll(Arrays.asList("SR_NO", "RunStatus", "Process", "HOST", "Password", "MAIL_TO",
						"MAIL_CC", "SUBJECT", "BODY_MESSAGE"));

				List<String> notPresentColumn = new ArrayList<>();

				Boolean allColumnPresent = true;

				for (String columnName : actualColumnName) {
					if (!exceptedColumnName.contains(columnName)) {
						notPresentColumn.add(columnName);
						allColumnPresent = false;
					}
				}

				if (allColumnPresent) {
//							System.out.println("All the columnName are present");
					recordset.close();
				} else {
					System.out.println("SORRY!!! " + notPresentColumn + " columns are not present inside MainController MailSend sheet");
					System.exit(0);
				}
			}
			try {
				recordset = conn.executeQuery(queryForProcessName);// first ea line execute haba but ea line fail hele
																	// direct catch block ku jiba
				if (recordset != null) {
//							System.out.println("MAIL_SEND sheet are present And Process name also same");

					List<Object> rowlist = new ArrayList<Object>();

					while (recordset.next()) {
						List<String> columns = recordset.getFieldNames();
						List<Object> rowvalues = new ArrayList<Object>();

						for (String column : columns) {
							rowvalues.add(recordset.getField(column));
						}
						rowlist.add(rowvalues);
//								System.out.println(rowvalues);
					}

					for (int i = 0; i < rowlist.size(); i++) {

						List<Object> row = (List<Object>) rowlist.get(i);

						Sr_No = (String) row.get(0);
						Process = (String) row.get(2);
						HOST = (String) row.get(3);
						Password = (String) row.get(4);
						MAIL_TO = (String) row.get(5);
						MAIL_CC = (String) row.get(6);
						SUBJECT = (String) row.get(7);
						BODY_MESSAGE = (String) row.get(8);
						

						if (Sr_No != null && !Sr_No.isEmpty() && Process != null && !Process.isEmpty() && HOST != null
								&& !HOST.isEmpty() && Password != null && !Password.isEmpty() && MAIL_TO != null
								&& !MAIL_TO.isEmpty() && MAIL_CC != null && !MAIL_CC.isEmpty() && SUBJECT != null
								&& !SUBJECT.isEmpty() && BODY_MESSAGE != null && !BODY_MESSAGE.isEmpty()) {

//									if (Process.equalsIgnoreCase(ConnectToMainController.process)) {
							MailSend_WithoutAnd_WithAttachment();// here to call the mail send method to send
																	// the mail
//									} else {
//										System.out.println(
//												"SORRY!!! Process Name Not Same to the Mail_Send Sheet Process Name");
//									}
						} else {
							System.out.println("Please Fill all the column to the MailSend Sheet");
						}

					}

					if (recordset != null) {
						recordset.close();
					}
					if (conn != null) {
						conn.close();
					}
				}
			} catch (Exception e) {
				System.out.println("SORRY!!! MAIL_SEND sheet are present BUT problem may be RunStatus or ProcessName");
			}

		} catch (Exception e) {
			System.out.println("SORRY!!!  MAIL_SEND sheet are not present...");
			System.exit(0);
		}
	}

	public static void MailSend_WithoutAnd_WithAttachment() {

		final String username = HOST; // "biswajitkanha11@gmail.com";
		final String password = Password;// "zfbaqijmtutiejyd";
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(HOST));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MAIL_TO));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(MAIL_CC));
			message.setSubject(SUBJECT);// "Automation Framework Report"

			// Create MimeMultipart for the message content
			Multipart multipart = new MimeMultipart("mixed");

			// HTML Body Part
			BodyPart htmlPart = new MimeBodyPart();
			
			String htmlContent = "<html><body><h2>" 
			        + ConnectToMainController.ApplicationName.toUpperCase() 
			        + " - Mobile Automation Test Report</h2>"
			        + "<p>Hi Team, </p><p> " + BODY_MESSAGE + " </p>"
			        + "<TABLE style=\"border-collapse: collapse; border: 1px solid black; background-color:#E4E5E5;\" width=\"100%\">"
			        + "<tr>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#4CAF50; color: white;\">Project</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#FFD700; color: black;\">Execution Type</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#00BFFF; color: white;\">Device Platform</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#1E90FF; color: white;\">Total TCs</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#4CAF50; color: white;\">Passed TCs</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#FF6347; color: white;\">Failed TCs</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#4682B4; color: white;\">Total Validations in all the TCs</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#32CD32; color: white;\">Passed Validations</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#FF6347; color: white;\">Failed Validations</th>"
			        + "<th style=\"text-align:center; border: 1px solid black; background-color:#4CAF50; color: white;\">Execution Time</th>" 
			        + "</tr>"
			        + "<tr>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToMainController.ApplicationName.toUpperCase() + "</td>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToMainController.ExecutionType.toUpperCase() + "</td>" 
				    + "<td style=\"text-align:center; border: 1px solid black;\">" + MobileConfiguration.DevicePlatform.toUpperCase() + "</td>" 
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.totalTest + "</td>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.pass + "</td>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.fail + "</td>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.totalValidations + "</td>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.passValidations + "</td>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + ConnectToDataSheet.failedValidations + "</td>"
			        + "<td style=\"text-align:center; border: 1px solid black;\">" + UtilsActivity.TotalExecutionTime + "</td>"
			        + "</tr>"
			        + "</TABLE><br><br></body></html>";


			

			htmlPart.setContent(htmlContent, "text/html");
			multipart.addBodyPart(htmlPart);

			message.setContent(multipart);
			Transport.send(message);

//			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
