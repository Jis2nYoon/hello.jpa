package hellojpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Member {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="USERNAME")
	private String name;
	
	private int age;
	
	@Enumerated(EnumType.STRING)
	private MemberType memberType;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@Lob
	private String lobString;
	
	@Lob
	private byte[] lobByte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getLobString() {
		return lobString;
	}

	public void setLobString(String lobString) {
		this.lobString = lobString;
	}

	public byte[] getLobByte() {
		return lobByte;
	}

	public void setLobByte(byte[] lobByte) {
		this.lobByte = lobByte;
	}
	
	
}
