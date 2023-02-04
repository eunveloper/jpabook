package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오전 1:08
 */
@Repository
public class MemberRepository {

    // 순수 자바 환경에서는 엔티티 매니저 팩토리에서 엔티티 매니저를 직접 생성하여 사용하였다.
    // 하지만 스프링 환경에서는 스프링 컨테이너가 엔티티 매니저를 관리하고 제공해준다.
    // 즉, 스프링 컨테이너가 제공하는 엔티티 매니저를 사용해야 한다.
    // 그러기 위해 @PersistenceContext 어노테이션을 사용하여 엔티티 매니저를 주입해준다.
    // 만약 엔티티 매니저 팩토리를 바로 주입받고 싶다면 @PersistenceUnit 어노테이션을 사용한다.
    @PersistenceContext
    EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // JPQL을 이용하였다.
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
