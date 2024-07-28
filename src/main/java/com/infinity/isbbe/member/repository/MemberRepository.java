package com.infinity.isbbe.member.repository;

import com.infinity.isbbe.member.aggregate.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
}
