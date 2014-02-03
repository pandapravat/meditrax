package com.pravat.test.bi.dao;

import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pravat.meditrax.bi.dao.AppConfigDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class ConfigDaoTest {
	
	@Resource
	AppConfigDao drugsDao;
	
	@Test
	public void testSpring() {
		Properties properties = System.getProperties();
		Set<Object> keySet = properties.keySet();
//		drugsDao.createObjects();
		for (Object object : keySet) {
			Object object2 = properties.get(object);
			
			System.out.println(object + "=" + object2);
		}
	}

}
