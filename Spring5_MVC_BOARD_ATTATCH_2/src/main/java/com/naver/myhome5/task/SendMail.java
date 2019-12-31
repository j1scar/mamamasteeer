package com.naver.myhome5.task;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class SendMail {

	@Autowired
	private JavaMailSenderImpl mailSender;

	//@Value("${sendfile}")
	//private String sendfile;

	public void sendMail(MailVO vo) {
		MimeMessagePreparator mp = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				// 두 번째 인자 true는 멀티 파트 메세지를 사용하겠다는 의미이다.
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(vo.getFrom());
				helper.setTo(vo.getTo());
				helper.setSubject(vo.getSubject());

				// 1. 문자로만 전송하는 경우
				helper.setText(vo.getContent(), true);// 두 번째 인자는 html을 사용하겠다는 뜻이다.
			}

		};

		mailSender.send(mp);
		System.out.println("메일 전송했습니다.");
	}
}
