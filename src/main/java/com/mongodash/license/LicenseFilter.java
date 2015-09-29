package com.mongodash.license;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.GenericFilterBean;

import com.mongodash.Config.SETTINGS_KEY;
import com.mongodash.model.LicenseKeySettings;
import com.mongodash.service.SettingsService;

public class LicenseFilter extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(LicenseFilter.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private static final String licenseURL = "/settings/license";
	private static final String licenseSaveURL = "settings/license/save";

	private SettingsService settings;
	
	public LicenseFilter(SettingsService settings) {
		this.settings = settings;
	}
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (logger.isDebugEnabled())
			logger.debug("----- > " + request.getRequestURI());

		boolean licensed = false;
		if (!request.getRequestURI().endsWith(licenseURL) && !request.getRequestURI().endsWith(licenseSaveURL)) {

			LicenseKeySettings ls = (LicenseKeySettings) settings.getSettings(SETTINGS_KEY.licenseKey);
			if (ls != null) {
				String privateKey = ls.getPrivateKey();

				// TODO HASH DIGEST - validate expiration date...
				if ("1234567890".equals(privateKey)) licensed = true;
			}

			if (!licensed) {
				redirectStrategy.sendRedirect(request, response, licenseURL);
				return;
			}
		}
		chain.doFilter(request, response);
	}
}