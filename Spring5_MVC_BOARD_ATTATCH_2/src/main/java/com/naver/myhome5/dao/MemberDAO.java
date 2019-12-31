package com.naver.myhome5.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.myhome5.domain.Member;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int insert(Member m) {
		return sqlSession.insert("Members.insert", m);
	}
	
	public Member isId(String id) {
		return sqlSession.selectOne("Members.idcheck", id);
	}

	public List<Member> getSearchList(Map<String, Object> map) {
		return sqlSession.selectList("Members.getSearchList", map);
	}

	public int getSearchListCount(Map<String, String> map) {
		return sqlSession.selectOne("Members.searchcount", map);
	}

	public Member memberinfo(String id) {
		return sqlSession.selectOne("Members.memberinfo", id);
	}

	public int delete(String id) {
		return sqlSession.delete("Members.delete", id);
	}

	public int update(Member m) {
		return sqlSession.update("Members.update", m);
	}
}
