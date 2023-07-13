package com.readingbooks.web.service.member;

import com.readingbooks.web.domain.entity.member.Member;
import com.readingbooks.web.domain.enums.Gender;
import com.readingbooks.web.exception.member.MemberPresentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void whenRegisteringFormNull_thenThrowException(){
        RegisterRequest request = createRequest(null, null, null, null, null, null);
        assertThatThrownBy(
                ()-> memberService.register(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenRegisteringInvalidEmail_thenThrowException(){
        RegisterRequest request = createRequest("tes@example.com", "test1234", "test1234", "test", "1999", Gender.SECRET);
        assertThatThrownBy(()->memberService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일을 올바르게 입력해주세요.");

    }

    @Test
    void whenRegisteringInvalidPasswordLength_thenThrowException(){
        RegisterRequest request = createRequest("test@example.com", "test","test", "test", "1999", Gender.SECRET);
        assertThatThrownBy(()->memberService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호를 올바르게 입력해주세요. 비밀번호는 8-16자에 특수문자 '@, $, !, %, *, #, ?, &'가 포함되야 합니다.");
    }

    @Test
    void whenRegisteringNotMatchedPasswords_thenThrowException(){
        RegisterRequest request = createRequest("test@example.com", "test1234", "helloworld1234", "test1234", "1999", Gender.SECRET);
        assertThatThrownBy(()->memberService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void whenRegisteringInvalidNameLength_thenThrowException(){
        RegisterRequest request = createRequest("test@example.com", "test1234", "test1234","t", "1999",  Gender.SECRET);
        assertThatThrownBy(()->memberService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름을 올바르게 입력해주세요.");
    }

    @Test
    void whenRegisteringInvalidBirthyearLength_thenThrowException(){
        RegisterRequest request = createRequest("test@example.com", "test1234!", "test1234!", "test", "19990115",  Gender.SECRET);
        assertThatThrownBy(()->memberService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("생년을 올바르게 입력해주세요.");
    }

    @Test
    void whenRegisteringGenderNull_thenThrowException(){
        RegisterRequest request = createRequest("test@example.com", "test1234!", "test1234!","test", "1999",  null);
        assertThatThrownBy(()->memberService.register(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("성별을 올바르게 입력해주세요.");
    }

    @Test
    void whenRegisteringExistedEmail_thenThrowException(){
        //given
        RegisterRequest registerMember = createRequest("test@example.com", "test1234!","test1234!", "test", "1999",  Gender.SECRET);
        memberService.register(registerMember);

        RegisterRequest request = createRequest("test@example.com", "test1234", "test1234", "test", "1999", Gender.MEN);

        assertThatThrownBy(() -> memberService.register(request))
                .isInstanceOf(MemberPresentException.class)
                .hasMessageContaining("이미 가입된 이메일입니다.");
    }

    @Test
    void whenMemberRegistered_thenVerifyFields(){
        //given
        RegisterRequest request = createRequest("success@example.com", "test1234!", "test1234!","test", "1999",  Gender.SECRET);

        //when
        Long memberId = memberService.register(request);
        Member findMember = memberService.findMember(memberId);

        //then
        assertThat(findMember.getEmail()).isEqualTo("success@example.com");
        assertThat(passwordEncoder.matches(request.getPassword(), findMember.getPassword())).isTrue();
        assertThat(findMember.getName()).isEqualTo("test");
        assertThat(findMember.getBirthYear()).isEqualTo("1999");
        assertThat(findMember.getGender()).isEqualTo(Gender.SECRET);
    }

    private RegisterRequest createRequest(String email, String password, String passwordConfirm, String name, String birthYear, Gender gender){
        return new RegisterRequest(email, password, passwordConfirm, name, birthYear, gender);
    }
}