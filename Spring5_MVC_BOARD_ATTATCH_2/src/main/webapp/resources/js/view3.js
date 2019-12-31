
   $("#comment table").hide(); //1
   
   function getList(){
	   $.ajax({
		   type:"post",
		   url:"CommentList.bo",
		   data:{"BOARD_RE_REF" : $("#BOARD_RE_REF").val()},
	       dataType:"json",
	       success:function(rdata){
	    	   if(rdata.length>0){
	    		   $("#comment table").show(); //문서가 로딩될 때 hide()햇던 부분 보이게한다
	    	       $("#comment thead").show(); //글이 있을 떄 hide() 부분을 보이게 한다.
	    	       $("#comment tbody").empty();
	    	       $("#message").text('');
	    	       output = '';
	    	       $(rdata).each(function(){
	    	    	   img='';
	    	    	   if($("#loginid").val()==this.id){ //id가 같은 사람에게만 보여준다
	    	    		   img = "<img src='resources/image/pencil.png' width='15px' class='update'>"
	    	    			   + "<img src='resources/image/remove.png' width='15px' class='remove'>"
	    	    			   + "<input type='hidden' value='"+this.num+"'>";
	    	    	   }
	    	    	   output += "<tr><td>"+this.id+"</td>";
	    	    	   output += "<td>"+this.content+"</td>";
	    	    	   output += "<td>"+this.reg_date+img+"</td></tr>";
	    	       });
	    	       $("#comment tbody").append(output);
	    	       
	    	   }else{
	    		   $("#message").text("등록된 댓글이 없습니다.")
	    		   $("#comment thead").hide(); //2
	    		   $("#comment tbody").empty();
	    	   }
	    	   $("#count").text(rdata.length);
	       }
	   
	   }) //ajax end
   
   };

 $(".start").click(function(){
	 getList();
 }); //click end

 //글자 수 50자 제한하는 이벤트
 $("#content").on('input', function(){
	 length = $(this).val().length;
	 if(length>50){
		 length=50;
		 content=content.substring(0, length);
	 }
	 $(".float-left").text(length+"/50")
 });
 
 //등록 또는 수정완료 버튼을 클릭한 경우
 //버튼의 라벨이 '등록'인 경우는 댓글을 추가하는 경우
 //버튼의 라벨이 '수정완료'인 경우는 댓글을 수정하는 경우
 $("#write").click(function(){
	 buttonText = $("#write").text(); //버튼의 라벨로 add할 지 update할 지
	 content = $("#content").val();
	 $(".float-left").text('총 50자까지 가능합니다.');
	
	 if(buttonText=="등록"){ //댓글을 추가하는 경우
		 url="CommentAdd.bo";
		 data={"content":content,
				 "id":$("#loginid").val(),
				 "BOARD_RE_REF":$("#BOARD_RE_REF").val()};		 
	 }else{//댓글을 수정하는 경우
		 url="CommentUpdate.bo";
		 data={"num":num, "content":content};
		 $("#write").text("등록"); //다시 등록으로 변경
	 }
	 $.ajax({
		 type:"post",
		 url:url,
		 data:data,
		 success:function(result){
			
			 $("#content").val('');
			 if(result==1){
				 getList();
			 }
		 }
	 }) //ajax end
 
 }) //$("#write") end
 
 //pencil2.png 클릭해서 수정하는 경우
 $("#comment").on('click','.update',function(){
	 before = $(this).parent().prev().text(); //선택한 내용을 가져온다
	 $("#content").focus().val(before); //textarea에 수정전 내용을 보낸다
	 num = $(this).next().next().val(); //수정할 댓글 번호를 저장한다
	 $("#write").text("수정완료"); //등록버튼의 라벨을 '수정완료'로 변경한다
	 $(this).parent().parent().css('background','lightgray');	 
 })

 //remove.png를 클릭해서 삭제하는 경우
 $("#comment").on('click','.remove', function(){
	 var num = $(this).next().val(); //댓글번호
	 
	 $.ajax({
		 type:"post",
		 url:"CommentDelete.bo",
		 data:{"num":num},
		 success:function(result){
			 if(result==1){
				 getList();
			 }
		 }
	 }) //ajax end
 })