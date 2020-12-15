package ggitlab.domain;

import java.sql.Date;

public class MemberBuilder {
	private String id;
	private String password;
	private String salt;
	private String email;
	private char type;
	private Date registerDate;
	private Date modifiedDate;

	public MemberBuilder id(String id) {
		this.id = id;
		return this;
	}

	public MemberBuilder password(String password) {
		this.password = password;
		return this;
	}

	public MemberBuilder salt(String salt) {
		this.salt = salt;
		return this;
	}

	public MemberBuilder email(String email) {
		this.email = email;
		return this;
	}

	public MemberBuilder type(char type) {
		this.type = type;
		return this;
	}

	public MemberBuilder registerDate(Date registerDate) {
		this.registerDate = registerDate;
		return this;
	}

	public MemberBuilder modifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
		return this;
	}

	public Member build() {
		Member member = new Member(id, password, salt, email, type, registerDate, modifiedDate);
		return member;
	}
}
