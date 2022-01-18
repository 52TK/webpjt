package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.config.Role;
import com.ydn.EaglesCM.dao.MemberRepository;
import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username).get();
    }

    /**
     *  회원 중복 체크
     * @param loginId
     * @param nickname
     * @param email
     */
    public void isDuplicateMember(String loginId, String nickname, String email) {

        if( memberRepository.existsByLoginId(loginId)) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        } else if(memberRepository.existsByNickname(nickname)) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        } else if(memberRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }
    }

    /**
    * 회원가입
     * @param memberSaveFrom
     */
    @Transactional
    public void save(MemberSaveForm memberSaveFrom) throws IllegalStateException{

        isDuplicateMember(
                memberSaveFrom.getLoginId(),
                memberSaveFrom.getNickname(),
                memberSaveFrom.getEmail()
        );

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(
            memberSaveFrom.getLoginId(),
            bCryptPasswordEncoder.encode(memberSaveFrom.getLoginPw()),
            memberSaveFrom.getName(),
            memberSaveFrom.getNickname(),
            memberSaveFrom.getEmail(),

            Role.MEMBER


        );

        memberRepository.save(member);
    }

    public Member findByLoginId(String loginId) throws IllegalStateException{

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        memberOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );

        return memberOptional.get();

    }







}


