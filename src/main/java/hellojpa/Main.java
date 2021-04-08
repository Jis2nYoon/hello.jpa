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
			/**
			 * JPQL
			 * 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
			 * SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
			 * 객체 지향 SQL
			 * ##from절에 들어가는게 객체라는것.
			 * ##엔티티와 속성은 대소문자 구분, 별칭 필수
			 */
			String jpql = "select m from Member m where m.name like '%hello%'";
			List<Member> result = em.createQuery(jpql, Member.class).getResultList();//getSingleResult()
			String usernameParam = "param";
			// 네임으로 파라미터 바인딩
			String jpql1 = "select m From Member m where m.name=:username";
			em.createQuery(jpql1, Member.class).setParameter("username", usernameParam);
			// 순서로 파라미터 바인딩
			String jpql2 = "select m From Member m where m.name=?1";
			em.createQuery(jpql2, Member.class).setParameter(1, usernameParam);
			//프로젝션
			//select m from Member m -> 엔티티 프로젝션
			//select m.team from Member m -> 엔티티 프로젝션
			//select username, age from Member m -> 단순 값 프로젝션
			
			//new 명령어: 단순 값을 DTO로 바로 조회
			//select ne jpabook.jpql.UserDTO(m.name,m.age) from Member m
			
			//DISTINCT는 중복 제거
			
			
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
