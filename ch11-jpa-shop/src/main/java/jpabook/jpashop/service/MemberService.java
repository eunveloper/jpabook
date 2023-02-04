package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오전 1:07
 */
@Service
// 해당 어노테이션이 붙어있으면 클래스나 메소드에 트랜잭션을 적용한다.
// 메소드를 호출할 때 트랜잭션을 시작하고 메소드를 종료할 때 트랜잭션을 커밋한다.
// 예외가 발생하면 트랜잭션을 롤백한다.
@Transactional
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(Member member) {

        validateDuplicateMember(member); // 동일한 이름을 가진 회원이 있다면 예외처리
        memberRepository.save(member);  // 저장
        return member.getId();
    }

    // 검증로직이 있더라도 멀티 스레드 상황을 고려하여 컬럼 자체에 유니크 제약조건을 추가하는 것이 안전하다.
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
