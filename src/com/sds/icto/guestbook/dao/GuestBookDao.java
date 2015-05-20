package com.sds.icto.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.sds.icto.guestbook.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	SqlMapClientTemplate sqlMapClientTemplate;
	
	public void insert(GuestBookVo vo) {
		sqlMapClientTemplate.insert("guestbook.insert", vo);

	} // insert

	// id 받아서 지울 때
	@SuppressWarnings("unchecked")
	public void delete(Long no, String password) {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put( "n", no );
		map.put( "p", password );
		
		sqlMapClientTemplate.delete("guestbook.delete", map);
	}

	// 전부 다 지울 때
	public void delete() throws ClassNotFoundException, SQLException {
	}

	public List<GuestBookVo> fetchList() {
		@SuppressWarnings("unchecked")
		List<GuestBookVo> list =
			sqlMapClientTemplate.queryForList("guestbook.list");
		return list;
	}

}
