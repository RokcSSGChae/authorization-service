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
import ggitlab.domain.MemberBuilder;
import ggitlab.repository.MemberRepository;
import ggitlab.utils.SecurityUtils;

@Service
public class FindPasswordService {

	private static final Long REDIS_KEY_EXPIRE_TIME = 1000L * 60 * 24;
	private static final String REDIS_KEY_PREFIX = "verify_";

	private static final String MAIL_TITLE = "[비밀번호 찾기] GGITLAB 이메일 인증";
	private static final String MAIL_GUIDE = "님 안녕하세요.<br> 비밀번호를 변경하시려면 다음 링크를 클릭해주세요.<br>";
	private static final String MAIL_LINK_PREFIX = "<a href='http://localhost:8888/findpassword/verify?";
	private static final String ID_QUERY_STRING = "id=";
	private static final String KEY_QUERY_STRING = "&key=";
	private static final String MAIL_LINK_SUFFIX = "'>비밀번호 변경하기</a></p>";

	private static final String MAIL_ENCODING_TYPE = "UTF-8";
	private static final String MAIL_SUB_TYPE = "html";

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
		message.setSubject(MAIL_TITLE);
		String salt = SecurityUtils.getSalt();
		String changePasswordkey = SecurityUtils.getEncrypted(salt, salt.getBytes(StandardCharsets.UTF_8));
		String redisKey = REDIS_KEY_PREFIX + registeredMember.getId();
		redisTemplate.opsForValue().set(redisKey, changePasswordkey);
		redisTemplate.expire(redisKey, REDIS_KEY_EXPIRE_TIME, TimeUnit.MILLISECONDS);

		StringBuilder mailContent = new StringBuilder()
				.append(registeredMember.getId())
				.append(MAIL_GUIDE)
				.append(MAIL_LINK_PREFIX)
				.append(ID_QUERY_STRING + registeredMember.getId())
				.append(KEY_QUERY_STRING + changePasswordkey)
				.append(MAIL_LINK_SUFFIX);
		message.setText(mailContent.toString(), MAIL_ENCODING_TYPE, MAIL_SUB_TYPE);
		javaMailSender.send(message);
	}

	public boolean verifyMail(String id, String key) {
		String redisKey = REDIS_KEY_PREFIX + id;
		if (redisTemplate.opsForValue().get(redisKey).equals(key)) {
			redisTemplate.delete(redisKey);
			return true;
		}
		return false;
	}
}
