package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService service;
    @Autowired
    private MemberRepository repository;
    
    @Test
    void 회원가입(){
        // given
        Member member = new Member();
        member.setName("member");
        
        // when
        Long savedId =  service.join(member);

        // then
        Assertions.assertThat(member).isEqualTo(repository.findOne(savedId));
    }


    @Test
    void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("member");
        Member member2 = new Member();
        member2.setName("member");

        // when
        service.join(member1);


        // then
       assertThrows(IllegalStateException.class, ()->{
           service.join(member2);
       });
    }
    
    

}