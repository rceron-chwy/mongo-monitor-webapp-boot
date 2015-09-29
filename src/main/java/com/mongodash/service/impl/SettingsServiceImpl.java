package com.mongodash.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.dao.SettingsDao;
import com.mongodash.event.MongoEventBus;
import com.mongodash.model.EmailSettings;
import com.mongodash.model.Notification;
import com.mongodash.model.NotificationType;
import com.mongodash.model.Response;
import com.mongodash.model.Settings;
import com.mongodash.service.SettingsService;
import com.mongodash.util.FileUtils;
import com.mongodash.util.PasswordUtil;

@Service
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	SettingsDao settingsDao;

	@Override
	public Settings getSettings(SETTINGS_KEY settingsKey) {
		return settingsDao.getSettings(settingsKey);
	}

	public void saveSettings(Settings config) {
		settingsDao.saveSettings(config);

		Notification n = null;
		switch (config.getKey()) {
		case alerts:
			n = new Notification(NotificationType.ALERTS_SETTINGS_CHANGED);
			break;
		case email:
			n = new Notification(NotificationType.MAIL_SETTINGS_CHANGED);
			break;
		case ldap:
			n = new Notification(NotificationType.LDAP_SETTINGS_CHANGED);
			break;
		case notifications:
			n = new Notification(NotificationType.NOTIFICATIONS_SETTINGS_CHANGED);
			break;
		default:
			// do nothing

		}

		if (n != null) MongoEventBus.post(n);
	}

	@Override
	public boolean isConfigActive(SETTINGS_KEY settingsKey) {
		return settingsDao.isConfigActive(settingsKey);
	}

	@Override
	public Response testEmailSettings() {

		Response resp = new Response();

		EmailSettings emailSettings = (EmailSettings) getSettings(SETTINGS_KEY.email);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.timeout", "8500");

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		if (emailSettings.isSsl()) {
			mailSender.setJavaMailProperties(props);
			mailSender.setProtocol("smtps");
		} else {
			mailSender.setProtocol("smtp");
		}
		mailSender.setProtocol("smtps");
		mailSender.setHost(emailSettings.getServer());
		mailSender.setPort(Integer.parseInt(emailSettings.getPort()));
		mailSender.setUsername(emailSettings.getUsername());
		mailSender.setPassword(PasswordUtil.decrypt(emailSettings.getPassword()));
		resp.setMessage("Email sent successfully.");

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(new String[] { "thiago.locatelli@gmail.com", "rfceron@gmail.com" });
			helper.setSubject("Hello from MongoDASH");
			
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream("templates/email_template_test.html");
			String template = FileUtils.getStringFromInputStream(is);
			
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
			String dt = sdf.format(new Date());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			template = template.replace("{user}", auth.getName());
			template = template.replace("{date}", dt);
			helper.setText(template, true);
			mailSender.send(message);
		} catch (MailException ex) {
			ex.printStackTrace();
			resp.setStatus("FAILED");
			resp.setMessage(ex.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			resp.setStatus("FAILED");
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

}
