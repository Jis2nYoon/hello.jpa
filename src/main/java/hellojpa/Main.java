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
			
			//페이징 쿼리
			String jpql3 = "select m from Member m order by m.name desc";
			List<Member> resultList = em.createQuery(jpql3, Member.class)
					.setFirstResult(10) 
					.setMaxResults(20)
					.getResultList(); //10번째 부터 20개 가져와
			
			//집합과 정렬
			/*
			select
			 	COUNT(m), //회원수
			 	SUM(m.age), //나이 합
			 	AVG(m.age), //평균 나이
			 	MAX(m.age), //최대 나이
			 	MIN(m.age)  //최소 나이
			from Member m
			*/
			//GROUP BY, HAVING, ORDER BY 다 사용 가능
			
			//조인 가능 (문법이 조금 다름)
			//내부조인: SELECT m FROM Member m [INNER] JOIN m.team t
			//외부조인: SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
			//세타조인: SELECT COUNT(m) FROM Member m, Team t where m.username = t.name
			//참고:하이버네이트 5.1부터 세타 조인도 외부 조인 가능
			
			//현업에서 진짜 많이 쓰는 fetch 조인
			//별칭사용x, 엔티티 객체 그래프를 한번에 조회
			//JPQL: select m from Member m join fetch m.team
			//SQL: SELECT M.*, T* FROM MEMBER T INNER JOIN TEAM T ON M.TEAM_ID=T.ID
			
			String jpql4 = "select m from Member m join fetch m.team";
			List<Member> members = em.createQuery(jpql4, Member.class).getResultList();
			
			for(Member member : members) {
				//페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩 발생 안함
				System.out.println("username = " + member.getName() + ", " + "teamname = " + member.getTeam().getName());
			}
			
			//서브쿼리 지원
			//EXISTS, IN
			//BETWEEN, LIKE, IS NULL
			
			//CASE문 가능
			//ANSI표준 SQL문법 다 됨.
			//사용자 정의 함수 호출 가능
			
			//Named 쿼리 - 어노테이션
			List<Member> resultList2 = em.createNamedQuery("Member.findByUsername", Member.class)
					.setParameter("username", "회원1")
					.getResultList();
			//단순 String으로 넣을때보다 좋은점, 배포 전에 로컬에서 한번만 띄워봐도 sql문법 오류를 발견할 수 있다.
			
			//Named 쿼리 - XML에 정의
			int count = ((Number)em.createNamedQuery("Member.count").getSingleResult()).intValue();
			System.out.println("Member.count : " + count);
			
			
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
