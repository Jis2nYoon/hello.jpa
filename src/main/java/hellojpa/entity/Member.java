package hellojpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {
	
	@Id @GeneratedValue
	private Long id;
	
	@Column(name="USERNAME")
	private String name;
	
	private int age;
	
	
	//fetch = FetchType.LAZY 로 해주면 JOIN 안하고 member만 조회함. team 사용하는 시점에서 team 조회함, 지연로딩!!(현업에서권장하는방법)
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="TEAM_ID")
	private Team team;

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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", age=" + age + ", team=" + team + "]";
	}
	
	
}
