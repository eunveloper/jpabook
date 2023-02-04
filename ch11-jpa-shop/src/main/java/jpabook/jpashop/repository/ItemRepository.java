package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 9:48
 */

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    // 하나의 메서드로 생성과 수정을 모두 처리한다.
    // 식별자의 값의 존재 유무에 따라서 식별자가 없다면 생성(persist)를, 있다면 수정(merge)를 한다.
    // JPA 레파지토리 인터페이스를 상속받아 메소드 쿼리를 사용할 때도 생성 수정 구분없이 save() 함수를 이용하는데 동일한 방식이다.
    public void save(Item item) {
        // Item의 식별자 컬럼이 @GeneratedValue를 선언하여 자동 생성되게 구성했기 때문에 문제없이 동작할 수 있다.
        // 만약 자동 생성 어노테이션이 명시되어 있지 않다면, 식별자가 없다는 예외가 발생한다.
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}
