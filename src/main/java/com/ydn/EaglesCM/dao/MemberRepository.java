package com.ydn.EaglesCM.dao;

import com.ydn.EaglesCM.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository {

    Optional<Member> findByLoginId(String loginId);

}
