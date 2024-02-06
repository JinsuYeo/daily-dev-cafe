package com.jsyeo.dailydevcafe.repository;

import com.jsyeo.dailydevcafe.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
}
