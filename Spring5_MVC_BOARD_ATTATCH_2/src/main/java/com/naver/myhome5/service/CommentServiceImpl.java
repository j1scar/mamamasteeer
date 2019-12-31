package com.naver.myhome5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.myhome5.dao.CommentDAO;
import com.naver.myhome5.domain.Comment;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDAO dao;

	@Override
	public int getListCount(int bOARD_RE_REF) {
		return dao.getListCount(bOARD_RE_REF);
	}

	@Override
	public List<Comment> getCommentList(int bOARD_RE_REF) {
		// TODO Auto-generated method stub
		return  dao.getCommentList(bOARD_RE_REF);
	}

	@Override
	public int commentsInsert(Comment c) {
		// TODO Auto-generated method stub
		return dao.commentsInsert(c);
		
	}

	@Override
	public int commentsDelete(int num) {
		// TODO Auto-generated method stub
		return dao.commentDelete(num);
	}

	@Override
	public int commentsUpdate(Comment co) {
		// TODO Auto-generated method stub
		return dao.commentsUpdate(co);
	}


	
	
}
