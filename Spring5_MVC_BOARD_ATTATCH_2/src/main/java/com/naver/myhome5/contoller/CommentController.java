package com.naver.myhome5.contoller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.myhome5.domain.Comment;
import com.naver.myhome5.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping(value="CommentAdd.bo")
	public void CommentAdd(Comment co, HttpServletResponse response)
	       throws Exception {
		System.out.println(co.getBOARD_RE_REF());
		int ok = commentService.commentsInsert(co);
		System.out.println("ok"+ok);
		response.getWriter().print(ok);
	}
	
	
	/*
	 * @ResponseBody와 jackson을 이용하여 JSON 사용하기
	 * 
	 * @ResponseBody란?
	 * 메서드에 @ResponseBody Annotation이 되어있으면 return되는 값은 Voew를 통해서
	 * 출력되는 것이 아니라 HTTP Response Body에 직접 쓰여지게 된다.
	 * 예) HTTP 응답 프로토콜의 구조 HTTP/1.1
	 * Message Header
	 *  200OK => Start Line Content-Type:text/html => Message Header Connect
	 *  close Server : Apache Tomcat... Last-Modified : Mon,...
	 *  
	 *  Message Body
	 *   <html><head><title>Hello JSP</title></head><body>Hello JSP!</body></html>
	 *   =>
	 *   응답 결과를 html이 아닌 JSON으로 변환하여 Message Body에 저장하려면
	 *   스프링에서 제공하는 변환기를 사용해야한다.
	 *   이 변환기를 이용해서 자바 객체를 다양한 타입으로 변환하여 HTTP Response Body에 설정할 수 있다.
	 *   스프링 설정파일에 <mvc:annotation-driven>을 설정하면 변환기가 생성된다.
	 *   이 중에서 자바 객체를 JSON 응답 바디로 변환할 때는 
	 *   MappingJackson2HttpMessageConverter를 사용한다.
	 *   
	 *   @ResponseBody를 이용해서 각 메서드의 실행 결과는 JSON으로 변환되어
	 *   HTTP Response BODY에 설정된다.
	 */
	@ResponseBody
	@PostMapping(value = "CommentList.bo")
	public List<Comment> CommentList(@RequestParam("BOARD_RE_REF")
	                                   int BOARD_RE_REF) {
		List<Comment> list 
		= commentService.getCommentList(BOARD_RE_REF);
		return list;
	}
	
	@PostMapping(value="CommentUpdate.bo")
	public void CommentUpdate(Comment co, HttpServletResponse response)
		       throws Exception {
		int oo = commentService.commentsUpdate(co);
		response.getWriter().print(oo);
	}
	
	@ResponseBody
	@PostMapping(value="CommentDelete.bo")
	public int CommentDelete(@RequestParam("num")
                      int num) {
		int de = commentService.commentsDelete(num);
		return de;
	}
}
