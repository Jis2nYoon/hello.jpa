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
		//엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
		tx.begin(); // [트랜잭션] 시작 
		
		try {
			//팀 저장
			Team team = new Team();
			team.setName("TeamA");
			em.persist(team);
			
			//회원 저장
			Member member = new Member(); //엔티티를 생성한 상태(비영속)
			member.setName("member1");
			//1차 캐시에 저장됨
			em.persist(member); //엔티티를 영속
			member.setTeam(team);
			
			//1차 캐시에서 조회 (글로벌 캐시가 아님. 쓰레드 안에서만 유지)
			Member findMember = em.find(Member.class, member.getId());
			//캐시에 없으니 디비에서 조회
			Member findMember2 = em.find(Member.class, "member2");
			Member findMember22 = em.find(Member.class, "member2");
			
			System.out.println(findMember2 == findMember22); // 동일성 비교 true
			
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
