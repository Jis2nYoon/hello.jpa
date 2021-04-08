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
			
			//JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유
			// 중간에 JPQL 실행  이건 JPA가 제공하는 것이라 문제가 없음. BUT Mybatis, springJDBC 템플릿 같은걸 섞어 쓴다면 직접 flush를 해줘야한다.
			// 플러시는 영속성 컨텍스트를 비우는게 아니다, db와 동기화 하는 것임.
			TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
			List<Member> members = query.getResultList();

			em.remove(memberA); //엔티티 삭제
			
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
