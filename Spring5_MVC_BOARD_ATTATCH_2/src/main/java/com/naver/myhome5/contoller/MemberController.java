package com.naver.myhome5.contoller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.naver.myhome5.domain.Member;
import com.naver.myhome5.service.MemberService;
import com.naver.myhome5.task.MailVO;
import com.naver.myhome5.task.SendMail;
/*
 * @Component를 이용해서 스프링 컨테이너가 해당 클래스 객체를 생성하도록 설정할 수 있지만
 * 모든 클래스에 @Component를 할당하면 어떤 클래스가 어떤 역할을 수행하는지 파악하기
 * 어렵다. 스프링 프레임워크에서는 이런 클래스들을 분류하기 위해서
 * @Component를 상속하여 다음과 같은 세개의 애노테이션을 제공한다.
 * 
 *  1. @Controller - 사용자의 요청을 제어하는 Controller클래스
 *  2. @Reposistory - 
 *  3. @Service - 
 */
@Controller
public class MemberController {
	
	@Autowired
	MemberService memberservice;
	
	@Autowired
	private SendMail sendMail;
	/*
	 * @CookieValue(value="saveid", required=false) Cookie readCookie
	 * 이름이 saveid인 쿠키를 Cookie 타입으로 전달받는다.
	 * 지정한 이름의 쿠키가 존재하지 않을 수도 있기 때문에 required=false로 설정해야 한다.
	 * 즉, id 기억하기를 선택하지 않을 수도 있기 때문에 required=false로 설정해야 한다.
	 * required=true 상태에서 지정한 이름을 가진 쿠키가 존재하지 않으면
	 * 스프링 mvc는 익셉션을 발생시킨다.
	 */
	//로그인 폼 이동
	@RequestMapping(value="/login.net", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv, 
			  @CookieValue(value="saveid", required=false) Cookie readCookie) {
	if(readCookie != null){
		mv.addObject("saveid", readCookie.getValue());
	}
	mv.setViewName("member/loginForm");
	return mv;
}
	
	//회원가입 폼 이동
    @RequestMapping(value="/join.net", method = RequestMethod.GET)
	public String join() {
		return "member/joinForm";
	}

    //회원가입 처리
    @RequestMapping(value="/joinProcess.net", method = RequestMethod.POST)
	public void joinProcess(Member member, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	int result = memberservice.insert(member);
    	out.println("<script>");
    	//삽입이 된 경우
    	if(result==1) {
    		
    		MailVO vo = new MailVO();
    		vo.setTo(member.getEmail());
    		vo.setContent(member.getId() + "님 회원 가입을 축하합니다.");
    		sendMail.sendMail(vo);
    		
    		out.println("alert('회원가입을 축하합니다.');");
    		out.println("location.href='login.net';");
    	}else if(result == -1) {
    		out.println("alert('아이디가 중복되었습니다. 다시 입력하세요.');");
    		out.println("history.back()"); //비밀번호 제외한 다른 데이터는 유지된다.
    	}
    	out.println("</script>");
        out.close();
    }
    
    //회원가입 폼에서 아이디 검사
    @RequestMapping(value="/idcheck.net", method = RequestMethod.POST)
	public void idcheck(@RequestParam("id") String id, 
			HttpServletResponse response) throws Exception {
		int result = memberservice.isId(id);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
	}
    
    //로그인 처리
    @RequestMapping(value="/loginProcess.net", method= RequestMethod.POST)
    public String loginProcess(@RequestParam("id")String id, 
    		                   @RequestParam("password") String password,
    		                   @RequestParam(value="remember", defaultValue="") String remember,
    		                   HttpServletRequest request,
    		                   HttpServletResponse response,
    		                   HttpSession session) throws Exception {
    	int result = memberservice.isId(id, password);
    	System.out.println("결과는"+result);
    	
    	if(result==1) {
    		//로그인 성공
    		session.setAttribute("id", id);
    		Cookie savecookie = new Cookie("saveid", id);
    		if(!remember.equals("")) {
    			savecookie.setMaxAge(60*60);
    			System.out.println("쿠키 저장: 60*60초");
    		}else {
    			System.out.println("쿠키저장: 0");
    			savecookie.setMaxAge(0);
    		}
    		response.addCookie(savecookie);
    		return "redirect:BoardList.bo";
    	}else {
    		String message = "비밀번호가 일치하지 않습니다.";
    		if(result == -1) 
    			message = "아이디가 존재하지 않습니다.";
    		
    		response.setContentType("text/html;charset=utf-8");
    		PrintWriter out = response.getWriter();
    		out.println("<script>");
    		out.println("alert('"+message+"');");
    		out.println("location.href='login.net';");
            out.println("</script>");
            out.close();
            return null;
    	}
    }
    
    //회원목록
    @RequestMapping(value="/member_list.net")
    public ModelAndView memberList(
			@RequestParam(value="page", defaultValue="1", required=false) int page,
			@RequestParam(value="limit", defaultValue="3", required=false) int limit,
			@RequestParam(value="search_field", defaultValue="1") int index,
			@RequestParam(value="search_word", defaultValue="") String search_word,
			ModelAndView mv
			) throws Exception {
    	
    	List<Member> list = null;
    	int listcount = 0;
    	
    	 list = memberservice.getSearchList(index, search_word, page, limit);
    	 listcount = memberservice.getSearchListCount(index, search_word); // 총 리스트 수

	      //총 페이지 수
	      int maxpage = (listcount + limit - 1) / limit;
	      System.out.println("총 페이지수 : " + maxpage);

	      int startpage = ((page - 1) / 5) * 5 + 1;
	      System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

	      int endpage = startpage + 5 - 1;
	      System.out.println("현재 페이지에 보여줄 마지막 페이지 수 =" + endpage);

	      if (endpage > maxpage)
	         endpage = maxpage;

	      mv.setViewName("member/member_list");
	      mv.addObject("page", page);
	      mv.addObject("maxpage", maxpage);
	      mv.addObject("startpage", startpage);
	      mv.addObject("endpage", endpage);
	      mv.addObject("listcount", listcount);
	      mv.addObject("totallist", list);
	      mv.addObject("limit", limit);
	      mv.addObject("search_field", index);
	      mv.addObject("seach_word", search_word);
	     
		return mv;
	   
    }
    
    //회원정보
    @RequestMapping(value="/member_info.net")
    public ModelAndView memberinfo(
    		@RequestParam("id") String id,
    		HttpServletRequest request,
    		ModelAndView mv) throws Exception {
    	Member member = memberservice.memberinfo(id);
    	if(member == null) {
			System.out.println("상세보기 실패");
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "상세보기 실패입니다.");
		} else {
			System.out.println("상세보기 성공");
			mv.setViewName("member/member_info");
			mv.addObject("memberinfo", member);
		}
	    return mv;
    }
    
    //회원삭제
    @RequestMapping(value="/member_delete.net")
    public void delete(@RequestParam("id") String id, 
    			HttpServletResponse response) throws Exception{
  
   	 int result = memberservice.delete(id);
   	 
   	 //삭제 실패한 경우
   	 if(result == 0) {
   		 System.out.println("회원 삭제 실패");
   	 }
   	 
   	 //삭제 성공한 경우 - 글 목록 보기 요청을 전송하는 부분
   	 System.out.println("회원 삭제 성공");
   	 response.setContentType("text/html;charset=utf-8");
   	 PrintWriter out = response.getWriter();
   	 out.println("<script>");
		 out.println("alert('삭제 되었습니다.');");
		 out.println("location.href='member_list.net';");
		 out.println("</script>");
		 out.close();
		
    }
    

    //수정폼
    @RequestMapping(value="/member_update.net")
    public ModelAndView update(HttpSession session, ModelAndView mv) throws Exception {
    	String id = (String) session.getAttribute("id");
    	Member m = memberservice.memberinfo(id);
    	mv.setViewName("member/updateForm");
    	mv.addObject("memberinfo", m);
    	return mv;
    }
		
    //수정처리해줘
    @RequestMapping(value="/updateProcess.net", method= RequestMethod.POST)
    public ModelAndView updateProcess(Member member,
            HttpServletResponse response) throws Exception {
       response.setContentType("text/html;charset=utf-8");
       PrintWriter out = response.getWriter();
       int result = memberservice.update(member);
       out.println("<script>");
       //수정 된 경우
       if(result==1) {
           out.println("alert('수정 완료');");
           out.println("location.href='BoardList.bo';");
       }else {
    	   out.println("alert('수정 실패');");
           out.println("history.back()");
       }
       out.println("</script>");
       out.close();
      return null;
   }
    
    //로그아웃
    @RequestMapping(value="/logout.net", method=RequestMethod.GET)
    public String logout(HttpSession session) {
    	session.invalidate(); //세션종료
    	return "redirect:login.net";
    }
  
}
