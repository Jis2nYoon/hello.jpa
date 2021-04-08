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
			// 영속 엔티티 조회
			Member memberA = em.find(Member.class, "memberA");
			
			//영속 엔티티 데이터 수정
			memberA.setName("hi");
			memberA.setAge(10);
			
			//em.update(member) 이런 코드가 있어야 하지 않을까? 하겠지만 없어도 자동으로 update쿼리가 나간다.
			// 영속 컨텍스트(entityManager)에서 관리되는(캐시되는) 것들은 변경이 감지된다.
			
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
