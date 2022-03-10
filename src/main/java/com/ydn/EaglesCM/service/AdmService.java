package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.config.Role;
import com.ydn.EaglesCM.dao.MemberRepository;
import com.ydn.EaglesCM.dto.adm.MemberStatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdmService {

    private final MemberRepository memberRepository;

    public MemberStatDto getMemberStatDto(){

        return new MemberStatDto(
            memberRepository.count(),
            memberRepository.countTodayMember(),
            memberRepository.countByAuthorityLike(Role.ADMIN),
            memberRepository.countByAuthorityLike(Role.MEMBER)
        );

    }

}
