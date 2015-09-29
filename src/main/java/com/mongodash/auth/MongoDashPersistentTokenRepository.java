package com.mongodash.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import com.mongodash.dao.RememberMeDao;
import com.mongodash.model.RememberMeToken;

@Component
public class MongoDashPersistentTokenRepository implements PersistentTokenRepository {

	@Autowired
	private RememberMeDao rememberMeDao;

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		rememberMeDao.save(new RememberMeToken(token));
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken token = rememberMeDao.findBySeries(series);
		if (token != null) {
			token.setTokenValue(tokenValue);
			token.setDate(lastUsed);
			rememberMeDao.update(token);
		}
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		RememberMeToken token = rememberMeDao.findBySeries(seriesId);
		return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
	}

	@Override
	public void removeUserTokens(String username) {
		rememberMeDao.removeByUserName(username);
	}
}
