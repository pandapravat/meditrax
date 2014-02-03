/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pravat.meditrax.bi.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author pandpr02
 */
public class AbstractDaoBase {
	Log log = LogFactory.getLog(getClass());
	@Resource
	JdbcTemplate template;
	@Resource(name="queryMap")
	Map<String, String> queryMap;
	@Resource(name="initQueryMap")
	Map<String, String> initQueryMap;
	//@Resource(name="initRecordKeyValMap")
	//Map<String, String> initRecordKeyValMap;
}
