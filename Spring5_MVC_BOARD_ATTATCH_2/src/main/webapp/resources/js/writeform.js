$(document).ready(function(){
	var check = 0;
	
  //등록 버튼 클릭할 때 이벤트 부분
  $("form").submit(function(){
	
  if($.trim($("textarea").val())==""){
	  alert("내용을 입력하세요");
	  $("textarea").focus();
	  return false;
  }

  }); //submit end

  show();
  function show(){
	  //파일이름이 있는 경우 remove이미지를 보이게 하고 없는 경우는 보이지 않게 한다.
	  if($("#filevalue").text()==''){
		  $(".remove").css('display','none');
	  }else{
		  $(".remove").css('display','inline-block');
	  }
  }
  
  $("#upfile").change(function(){
	  check++;
	 var inputfile=$(this).val().split('\\');
	 $("#filevalue").text(inputfile[inputfile.length-1]);
     show();
  });
  
  //remove이미지를 클릭하면 파일명을 ''로 변경하고 remove 이미지를 보이지 않게 한다.
  $(".remove").click(function(){
	  $("#filevalue").text('');
	  $(this).css('display','none');
	  //추가된 부분
	  $("input[name=BOARD_ORIGINAL]").val('');
	  $("input[name=BOARD_FILE]").val('');
  })
  
}); //ready() end