package hellojpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
			Member memberA = new Member();
			memberA.setName("memberA");
			Member memberB = new Member();
			memberA.setName("memberB");
			Member memberC = new Member();
			memberA.setName("memberC");
			em.persist(memberA);
			em.persist(memberB);
			em.persist(memberC);
			
			em.flush();
			
			em.detach(memberA); //특정 엔티티만 준영속 상태로 전환
			//em.clear(); // 준영속 상태
			//em.close(); // 영속성 컨텍스트를 종료
			
			memberA.setName("memberAA");
			
			tx.commit(); // [트랜잭션] 커밋
		} catch (Exception e){
			tx.rollback();
			System.out.println("error!" + e);
		} finally {
			em.close();
		}
		
		emf.close();
	}

}
