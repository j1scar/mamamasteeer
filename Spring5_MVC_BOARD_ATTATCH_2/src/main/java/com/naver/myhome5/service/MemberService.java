package com.naver.myhome5.service;

import java.util.List;

import com.naver.myhome5.domain.Member;

public interface MemberService {

	public int isId(String id, String password);
	public int insert(Member m);
	public int isId(String id);
	public Member memberinfo(String id);
	public int delete(String id);
	public int update(Member m);
	public List<Member> getSearchList(int index, String search_word,
			                         int page, int limit);
	public int getSearchListCount(int index, String search_word);
}
