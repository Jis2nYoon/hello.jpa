package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

public class Main {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			//팀 저장
			Team team = new Team();
			team.setName("TeamA");
			em.persist(team);
			
			//회원 저장
			Member member = new Member();
			member.setName("member1");
//			member.setTeam(team); //단방향 연관관계 설정, 참조 저장
			em.persist(member);
			//역방향(주인이 아닌 방향)만 연관관계 설정
			team.getMembers().add(member);//자주 저지르는 실수 member.setTeam(team);을 해야하는데 반대로 set하는 경우
			//정방향(주인인 방향)
			member.setTeam(team);
			
			em.flush();//db에 쿼리를 다 보내버림
			em.clear();//캐시를 다 비워버림
			
			//조회
			Member findMember = em.find(Member.class, member.getId());
			
			// 참조를 사용해서 연관관계 조회
			Team findTeam = findMember.getTeam();
			
			findTeam.getName();
			
			List<Member> members = findTeam.getMembers();
			for(Member member1 : members) {
				System.out.println("member1 = " + member1.toString());
			}
			
			
			tx.commit();
		} catch (Exception e){
			tx.rollback();
			System.out.println("error!" + e);
		} finally {
			em.close();
		}
		
		emf.close();
	}

}
