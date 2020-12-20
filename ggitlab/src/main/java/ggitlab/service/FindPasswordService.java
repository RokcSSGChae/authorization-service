package ggitlab.service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import ggitlab.domain.Member;
import ggitlab.repository.MemberRepository;
import ggitlab.utils.SecurityUtils;

@Service
public class FindPasswordService {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	JavaMailSenderImpl javaMailSender;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	public void sendMail(String email) throws MessagingException {
		Member registeredMember = memberRepository.findByEmail(email);
		if (registeredMember == null) {
			return;
		}
		MimeMessage message = javaMailSender.createMimeMessage();
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		message.setSubject("[비밀번호 찾기] GGITLAB 이메일 인증");
		String salt = SecurityUtils.getSalt();
		String changePasswordkey = SecurityUtils.getEncrypted(salt, salt.getBytes(StandardCharsets.UTF_8));
		String redisKey = "changepw-" + registeredMember.getId();
		redisTemplate.opsForValue().set(redisKey, changePasswordkey);
		redisTemplate.expire(redisKey, 60 * 24 * 1000, TimeUnit.MILLISECONDS);

		StringBuilder mailContent = new StringBuilder()
				.append(registeredMember.getId() + "님 안녕하세요.<br>")
				.append("비밀번호를 변경하시려면 다음 링크를 클릭해주세요.<br>")
				.append("<a href='http://localhost:8080/auth/findpassword?")
				.append("username=" + registeredMember.getId() + "&key=" + redisKey)
				.append("'>비밀번호 변경하기</a></p>");

		message.setText(mailContent.toString(), "UTF-8", "html");
		javaMailSender.send(message);
	}
}
