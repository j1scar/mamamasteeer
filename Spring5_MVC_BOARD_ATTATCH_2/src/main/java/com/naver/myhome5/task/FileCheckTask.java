package com.naver.myhome5.task;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.naver.myhome5.service.BoardService;

@Service
public class FileCheckTask {

	@Value("${savefoldername}")
	private String saveFolder;
	
	@Autowired
	private BoardService boardService;

	//@Scheduled(fixedDelay=1000) //이전에 실행된 task 종료
	//밀리세컨드 단위
	public void test() throws Exception{
		System.out.println("test");
	}
	
	//cron 사용법
	//seconds(초:0~59) minutes(분:0~59) hours(시:0~23) day(일:1~31)
	//months(달:1~12) day of week(요일: 0~6) year(optional)
	// 초 분 시 일 달 요일
	//@Scheduled(cron="0 59 11 * * *")
	public void checkFiles() throws Exception{
		
		System.out.println("checkFiles");
		List<String> deleteFileList = boardService.getDeleteFileList();
		
		for(String filename: deleteFileList) {
			File file = new File(saveFolder + filename);
			if(file.exists()) {
				if(file.delete()) {
					System.out.println(file.getPath() + " 삭제되었습니다.");
				}
			}
		}
	}
		
}
