package com.mongodash.dao;

import com.mongodash.model.RememberMeToken;

public interface RememberMeDao extends BaseDao<RememberMeToken> {

	RememberMeToken findBySeries(String series);

	void removeByUserName(String username);

}
