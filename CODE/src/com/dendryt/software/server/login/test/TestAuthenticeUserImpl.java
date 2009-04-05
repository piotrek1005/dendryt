package com.dendryt.software.server.login.test;

import com.dendryt.software.client.login.Data;
import com.dendryt.software.server.login.AuthenticateUserImpl;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestAuthenticeUserImpl extends TestCase {
	AuthenticateUserImpl au=new AuthenticateUserImpl();
	public void testAuthenticate() {
		
		Assert.assertEquals(3, au.authenticate(new Data("heniek","heniek1")));
		Assert.assertTrue(au.authenticate(new Data("zle_dane","zale_haslo"))==0);
	}

}
