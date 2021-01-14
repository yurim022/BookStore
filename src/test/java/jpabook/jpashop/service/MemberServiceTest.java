package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest
{
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception
    {
        //given
        Member member = new Member();
        member.setName("yurim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertThat(memberRepository.findOne(saveId)).isEqualTo(member);
    }

    @Test
    void 중복_회원_예외() throws Exception
    {
        //given
        String name = "yurim";

        Member memberA = new Member();
        memberA.setName(name);

        Member memberB = new Member();
        memberB.setName(name);

        //when
        memberService.join(memberA);

        try {
            memberService.join(memberB);
        }
        catch (IllegalStateException e){
            return;
        }

        //then
        fail("예외가 발생해야 한다.");
    }
}