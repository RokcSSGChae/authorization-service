package ggitlab.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Member {

	@Id
	private String id;
	private String password;
	private String salt;
	private String email;
	private char type;

	@Column(name = "register_date", updatable = false)
	@CreationTimestamp
	private Date registerDate;

	@Column(name = "modified_date")
	@UpdateTimestamp
	private Date modifiedDate;

	public Member() {
	}

	public Member(String id, String password, String salt, String email, char type, Date registerDate,
			Date modifiedDate) {
		super();
		this.id = id;
		this.password = password;
		this.salt = salt;
		this.email = email;
		this.type = type;
		this.registerDate = registerDate;
		this.modifiedDate = modifiedDate;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public String getEmail() {
		return email;
	}

	public char getType() {
		return type;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}
}
