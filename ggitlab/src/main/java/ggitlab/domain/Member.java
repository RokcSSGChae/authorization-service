package ggitlab.domain;

import java.sql.Date;

public class Member {
	private String id;
	private String password;
	private String salt;
	private String email;
	private char type;
	private Date regdate;
	private Date dropdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Date getDropdate() {
		return dropdate;
	}

	public void setDropdate(Date dropdate) {
		this.dropdate = dropdate;
	}
}
