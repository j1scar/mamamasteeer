package com.naver.myhome5.contoller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.naver.myhome5.domain.Board;
import com.naver.myhome5.service.BoardService;
import com.naver.myhome5.service.CommentService;

@Controller
public class BoardController {

   @Autowired
   BoardService boardService;
   
   @Autowired
   private CommentService commentService;
   
   //추가
   //savefolder.properties
   //속성=값의 형식으로 작성하면 된다.
   //savefoldername = D:\\workspace-sts-3.9.10.RELEASE\\Spring5_MVC_BOARD_ATTATCH_2\\src\\main\\webapp\\resources\\upload\\
   //값을 가져오기 위해서는 속성(property)를 이용한다.
   @Value("${savefoldername}")
   private String saveFolder;
   
   //글쓰기
   @GetMapping(value="/BoardWrite.bo")
   public String board_write() throws Exception {
      return "board/qna_board_write";
      
   }
   
   //글 목록 보기
   @RequestMapping(value = "/BoardList.bo")
      public ModelAndView boardlist(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
            ModelAndView model) {

         int limit = 10; // 한 화면에 보여줄 갯수

         // 총 리스트 수를 받아옵니다.
         int listcount = boardService.getListCount(); // 총 리스트 수

         /*
          * 총 페이지 수 = (DB에 저장된 총 리스트의 수 + 한 페이지에서 보여주는 리스트의 수 -1)/ 한 페이지에서 보이는 리스트의 수
          * 
          * 예를 들어 한 페이지에서 보여주는 리스트의 수가 10개인 경우 예1) DB에 저장된 총 리스트의 수가 0이면 총 페이지의 수는 0페이지
          * 예2) DB에 저장된 총 리스트의 수가 (1~10)이면 총 페이지수는 1페이지 예3) DB에 저장된 총 리스트의 수가 (11~20) 이면
          * 총 페이지수는 2페이지 예4) DB에 저장된 총 리스트의 수가 (21~30) 이면 총 페이지수는 3페이지
          * 
          * 
          */

         int maxpage = (listcount + limit - 1) / limit;
         System.out.println("총 페이지수 : " + maxpage);

         /*
          * startpage : 현재 페이지 그룹에서 맨 처음에 표시될 페이지 수를 의미합니다. ([1], [11],[21] 등...) 보여줄
          * 페이지가 30개일 경우 [1][2][3]...[30]까지 다 표시하기에는 너무 많기 때문에 보통 한 페이지에는 10페이지 정도 까지
          * 이동할수 있게 표시합니다. 예)페이지 그룹이 아래와 같은 경우 [1][2][3][4][5][6][7][8][9][10] 페이지 그룹의 시작
          * 페이지는 startpage에 마지막 페이지는 endpage에 구합니다.
          * 
          * 예로 1~10 페이지의 내용을 나타낼때는 페이지 그룹은 [1][2][3]..[10]로 표시되고 11~20 페이지의 내용을 나타낼때는 페이지
          * 그룹은 [11][12][13]..[20]까지 표시됩니다.
          */

         int startpage = ((page - 1) / 10) * 10 + 1;
         System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

         // endpage: 현재 페이지 그룹에서 보여줄 마지막 페이지수([10],[20],[30]) 등
         int endpage = startpage + 10 - 1;
         System.out.println("현재 페이지에 보여줄 마지막 페이지 수 =" + endpage);

         /*
          * 마지막 그룹의 마지막페이지 값은 최대 페이지 값입니다. 예로 마지막 페이지 그룹이 [21]~[30]인 경우 시작
          * 페이지(startpage=21)와 마지막페이지(endpage=30) 이지만 최대 페이지(maxpage)가 25라면 [21]~[25]까짐나
          * 표시되도록 합니다.
          */

         if (endpage > maxpage)
            endpage = maxpage;

         List<Board> list = boardService.getBoardList(page, limit);
         System.out.println("page =" + page);
         System.out.println("limit =" + limit);
         model.setViewName("board/qna_board_list");
         model.addObject("page", page);
         model.addObject("maxpage", maxpage);
         model.addObject("startpage", startpage);
         model.addObject("endpage", endpage);
         model.addObject("limit", limit);
         model.addObject("boardlist", list);
         model.addObject("listcount", listcount);
         return model;
      }
   
   @ResponseBody
   @RequestMapping(value="/BoardListAjax.bo")
   public Object boardListAjax(
         @RequestParam(value="page", defaultValue="1", required=false) int page,
         @RequestParam(value="limit", defaultValue="10", required=false) int limit
         ) throws Exception {
      

         // 총 리스트 수를 받아옵니다.
         int listcount = boardService.getListCount(); // 총 리스트 수

         //총 페이지 수
         int maxpage = (listcount + limit - 1) / limit;
         System.out.println("총 페이지수 : " + maxpage);

         //현재 페이지에 보여줄 시작 페이지 수
         int startpage = ((page - 1) / 10) * 10 + 1;
         System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

         // endpage: 현재 페이지 그룹에서 보여줄 마지막 페이지수([10],[20],[30]) 등
         int endpage = startpage + 10 - 1;
         System.out.println("현재 페이지에 보여줄 마지막 페이지 수 =" + endpage);

         if (endpage > maxpage)
            endpage = maxpage;

         List<Board> list = boardService.getBoardList(page, limit);

         //BoardAjax 이용하기
         /*
          System.out.println("BoardAjax 이용하기");
          BoardAjax ba = new BoardAjax();
          ba.setPage(page);
          ba.setMaxPage(maxpage);
          ba.setStartpage(startpage);
          ba.setEndpage(endpage);
          ba.setListcount(listcount);
          ba.setBoardList(boardlist);
          ba.setLimit(limit);
          */
         
         //Map 이용하기
         System.out.println("map 이용하기");
         Map<String, Object> ba = new HashMap<String, Object>();
            ba.put("page", page);
           ba.put("maxpage",maxpage);
          ba.put("startpage",startpage);
          ba.put("endpage",endpage);
          ba.put("listcount",listcount);
          ba.put("limit",limit);
          ba.put("boardlist",list);
         return ba;
      
   }
   
   //글 쓰기 -파일 업로드
   @PostMapping("/BoardAddAction.bo")
   public String bbs_write_ok(Board board, HttpServletRequest request)
                throws Exception{
      MultipartFile uploadfile = board.getUploadfile();
      
      if(!uploadfile.isEmpty()) {
         String fileName = uploadfile.getOriginalFilename(); //원래 파일명
         board.setBOARD_ORIGINAL(fileName); //파일명 저장
         
         //새로운 폴더 이름 : 오늘 년-월-일
         Calendar c = Calendar.getInstance();
         int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH)+1;
         int date = c.get(Calendar.DATE);
         
         //2.특정 폴더
         //String saveFolder = 
         //      "D:\\workspace-sts-3.9.10.RELEASE\\Spring5_MVC_BOARD_ATTATCH_2\\src\\main\\webapp\\resources\\upload\\";
         
         String homedir = saveFolder + year + "-" + month + "-" +date;
         System.out.println(homedir);
         File path1 = new File(homedir);
         if(!(path1.exists())) {
            path1.mkdir(); //새로운 폴더 생성
         }
         
         //난수를 구한다
         Random r = new Random();
         int random = r.nextInt(100000000);
         
         /**확장자 구하기 시작**/
         int index = fileName.lastIndexOf(".");
         //문자열에서 특정 문자열의 위치 값(index)를 반환한다.
         //indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
         //lastIndexOf는 마지막으로 발견되는 문자열의 index를 반환한다.
         //(파일면에 점이 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴한다.)
         System.out.println("index=" +index);
         String fileExtension = fileName.substring(index + 1);
         System.out.println("fileExtension = "+ fileExtension);
         /**확장자 구하기 끝 **/
         
         //새로운 파일 명
         String refileName = "bbs" + year + month + date + random + "." 
                                  + fileExtension;
         System.out.println("refileName="+refileName);
         
         //오라클 디비에 저장될 파일명
         String fileDBName = "/" + year + "-" + month + "-" + date + "/"
                             + refileName;
         System.out.println("fileDBName="+fileDBName);
         
         //transferTo(File path) : 업로드한 파일을 매개변수의 경로에 저장한다.
         uploadfile.transferTo(new File(saveFolder+fileDBName));
         
         //바뀐 파일명으로 저장
         board.setBOARD_FILE(fileDBName);
      }
      
      boardService.insertBoard(board); //저장메서드 호출
      
      return "redirect:BoardList.bo";
   }

   //상세보기
   //BoardDetailAction.bo?num9 요청시 파라미터 num의 값을 int num에 저장한다.
   @GetMapping("/BoardDetailAction.bo")
   public ModelAndView Detail(int num, ModelAndView mv,
         HttpServletRequest request) {
      boardService.setReadCountUpdate(num); //조회수
      Board board = boardService.getDetail(num);
      if(board == null) {
         System.out.println("상세보기 실패");
         mv.setViewName("error/error");
         mv.addObject("url", request.getRequestURL());
         mv.addObject("message", "상세보기 실패입니다.");
      } else {
         System.out.println("상세보기 성공");
         int count = commentService.getListCount(num);
         mv.setViewName("board/qna_board_view");
         mv.addObject("count", count);
         mv.addObject("boarddata", board);
      }
      return mv;
   }
   
   //글 답변
   @GetMapping("/BoardReplyView.bo")
   public ModelAndView BoardReplyView(int num, ModelAndView mv,
         HttpServletRequest request) {
      Board board = boardService.getDetail(num);
      if(board == null) {
         mv.addObject("url", request.getRequestURL());
         mv.addObject("message", "답변글 처리 실패입니다.");
      } else {
         System.out.println("답변 성공");
         mv.setViewName("board/qna_board_reply");
         mv.addObject("boarddata", board);
      }
      return mv;
   }
   
   @PostMapping("/BoardReplyAction.bo")
   public ModelAndView BoardReplyAction(Board board, ModelAndView mv, 
         HttpServletRequest request) throws Exception{
       //boardService.boardReply(board);
      int result = boardService.boardReply(board);
      if(result == 0) {
         mv.addObject("url", request.getRequestURL());
         mv.addObject("message", "답변글 처리 실패입니다.");
      }
      else {
         mv.setViewName("redirect:BoardList.bo");
      }
      return mv;
    }

   //글 수정
   @GetMapping("/BoardModifyView.bo")
   public ModelAndView BoardModifyView(int num, ModelAndView mv,
         HttpServletRequest request) throws Exception {
      
      Board boarddata = boardService.getDetail(num);
      //글 내용 불러오기 실패한 경우
      if(boarddata == null) {
         System.out.println("수정 상세보기 실패");
         mv.setViewName("error/error");
         mv.addObject("url", request.getRequestURL());
         mv.addObject("message", "수정 상세보기 실패입니다.");
      }
         System.out.println("수정 상세보기 성공");
            
         //수정 폼 페이지로 이동할 때 원문 글 내용을 보여주기 때문에 boarddata 객체를
         //ModelAndView 객체에 저장한다.
         mv.addObject("boarddata", boarddata);
         
         //글 수정 폼 페이지로 이동하기 위해 경로를 설정한다.
         mv.setViewName("board/qna_board_modify");
      return mv;
   }
   
   //글 수정
      @PostMapping("/BoardModifyAction.bo")
      public ModelAndView BoardModifyAction(Board board, String before_file, 
            ModelAndView mv, HttpServletRequest request, 
            HttpServletResponse response) throws Exception{
         boolean usercheck = 
               boardService.isBoardWriter(board.getBOARD_NUM(), board.getBOARD_PASS());
         
         //비밀번호가 다른 경우
         if(usercheck == false) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('비밀번호가 다르다.');");
            out.println("history.back();");
            out.println("</script>");
            out.close();
            return null;
         }
         
         MultipartFile uploadfile = board.getUploadfile();
         
         //1.
         //String saveFolder = request.getSession().getServletContext().getRealPath("resources") +"/upload/";
         
            if(uploadfile!=null && !uploadfile.isEmpty()) { //파일 변경한 경우
               System.out.println("파일 변경한 경우");
               String fileName = uploadfile.getOriginalFilename(); //원래 파일 이름
               board.setBOARD_ORIGINAL(fileName);
               
               String fileDBName = fileDBName(fileName, saveFolder);
               
               //transferTo(File path) : 업로드한 파일의 매개변수의 경로에 저장한다.
               uploadfile.transferTo(new File(saveFolder+fileDBName));
               
               //바뀐 파일명으로 저장
               board.setBOARD_FILE(fileDBName);
            }
         
         //DAO에서수정 메서드 호출하여 수정한다
         int result = boardService.boardModify(board);
         
         //수정에 실패한 경우
         if(result == 0) {
            System.out.println("게시판 수정 실패");
            mv.setViewName("error/error");
            mv.addObject("url", request.getRequestURL());
            mv.addObject("message", "게시판 수정 실패");
         }else { //수정 성공의 경우
            System.out.println("게시판 수정 완료");
            
            //추가
            //수정 전에 파일이 있고 새로운 파일을 선택한 경우는 삭제할 목록을 테이블에 추가한다.
            if(!before_file.equals("") &&
                   !before_file.equals(board.getBOARD_FILE())) {
                      boardService.insert_deleteFile(before_file);
                   }
            
            String url =
                  "redirect:BoardDetailAction.bo?num=" + board.getBOARD_NUM();
            
            //수정한 글 내용을 보여주기 위해 글 내용 보기 보기페이지로 이동하기 위해 경로를 설정한다
            mv.setViewName(url);
         }
         return mv;
      
      }
      
   @GetMapping("BoardFileDown.bo")
   public void BoardFileDown(String filename,
         HttpServletRequest request, String original,
         HttpServletResponse response) throws Exception {
      
      //서블릿의 실행 환경 정보를 담고있는 객체를 리턴한다.
      ServletContext context = 
            request.getSession().getServletContext();
      
      //1.
      //String savePath = "resources/upload";
      //String sDownloadPath = context.getRealPath(savePath);
      
      //String sFilePath = sDownloadFil
      //String sFilePath = sDownloadPath + "/" + filename;
      //System.out.println(sFilePath);
      
      //2.
      String sFilePath = saveFolder + "/" + filename;
      System.out.println(sFilePath);
      byte b[] = new byte[4096];
      
      //sFilePath에 있는 파일의 MimeType을 구해온다.
      String sMimeType = context.getMimeType(sFilePath);
      System.out.println("sMimeType>>>"+sMimeType);
      
      if(sMimeType == null) 
         sMimeType = "application/octet-stream";
      
      response.setContentType(sMimeType);
      
      //한글 깨짐 방지
      String sEncoding = 
            new String(original.getBytes("utf-8"), "ISO-8859-1");
      System.out.println(sEncoding);
      
      /*
       * Content-Disposition: attatchment:브라우저는 해당 Content를 처리하지 않고
       * 다운로드 한다.
       */
      response.setHeader("Content-Disposition", "attatchment; filename=" + sEncoding);
      
      //프로젝트 속성 - 자바버전 1.8로 변경
      try(
            //웹 브라우저로의 출력 스트림 생성한다.
            BufferedOutputStream out2 = 
            new BufferedOutputStream(response.getOutputStream());
            //sFilePath로 지정한 파일에 대한 입력 스트림을 생성한다.
            BufferedInputStream in 
            = new BufferedInputStream(new FileInputStream(sFilePath));)
      {
         int numRead;
         //read(b, 0, b.length):바이트 배열 b의 0번부터 b.length
         //크기만큼 읽어온다
         while ((numRead=in.read(b,0,b.length))!=-1) { //읽을 데이터가 존재
            //바이트 배열 b의 0번부터 numRead크기만큼 브라우저로 출력
            out2.write(b,0,numRead);
            
         }
      }catch (Exception e) {
         e.printStackTrace();
      }
   }

      
     private String fileDBName(String fileName, String saveFolder) {
        //새로운 폴더 이름 : 오늘 년 + 월 + 일
        Calendar c = Calendar.getInstance();
         int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH)+1;
         int date = c.get(Calendar.DATE);
         
         String homedir = saveFolder + year + "-" + month + "-" +date;
         System.out.println(homedir);
         File path1 = new File(homedir);
         if(!(path1.exists())) {
            path1.mkdir(); //새로운 폴더 생성
         }
         
         //난수를 구한다
         Random r = new Random();
         int random = r.nextInt(100000000);
         
         /**확장자 구하기 시작**/
         int index = fileName.lastIndexOf(".");
         //문자열에서 특정 문자열의 위치 값(index)를 반환한다.
         //indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
         //lastIndexOf는 마지막으로 발견되는 문자열의 index를 반환한다.
         //(파일면에 점이 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴한다.)
         System.out.println("index=" +index);
         String fileExtension = fileName.substring(index + 1);
         System.out.println("fileExtension = "+ fileExtension);
         /**확장자 구하기 끝 **/
         
         //새로운 파일 명
         String refileName = "bbs" + year + month + date + random + "." 
                                  + fileExtension;
         System.out.println("refileName="+refileName);
         
         //오라클 디비에 저장될 파일명
         String fileDBName = "/" + year + "-" + month + "-" + date + "/"
                             + refileName;
         System.out.println("fileDBName="+fileDBName);
         return fileDBName;
      }
     
     @PostMapping("BoardDeleteAction.bo")
     public String BoardDeleteAction(String BOARD_PASS, int num,
                             HttpServletResponse response) throws Exception {
        //글 삭제 명령을 요청한 사용자가 글을 작성한 사용자인지 판단하기 위해
        //입력한 비밀번호와 저장된 비밀번호를 비교하여 일치하면 삭제한다.
        boolean usercheck = boardService.isBoardWriter(num, BOARD_PASS);
        
        //비밀번호 일치하지 않는 경우
        if(usercheck == false) {
           response.setContentType("text/html;charset=utf-8");
           PrintWriter out = response.getWriter();
           out.println("<script>");
           out.println("alert('비밀번호가 다릅니다.');");
           out.println("history.back();");
           out.println("</script>");
           out.close();
           return null;
        }
        //비밀번호 일치하는 경우 삭제한다
        int result = boardService.boardDelete(num);
        
        //삭제 실패한 경우
        if(result == 0) {
           System.out.println("게시판 삭제 실패");
        }
        
        //삭제 성공한 경우 - 글 목록 보기 요청을 전송하는 부분
        System.out.println("게시판 삭제 성공");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
       out.println("alert('삭제 되었습니다.');");
       out.println("location.href='BoardList.bo';");
       out.println("</script>");
       out.close();
       return null;
        
     }
   
}