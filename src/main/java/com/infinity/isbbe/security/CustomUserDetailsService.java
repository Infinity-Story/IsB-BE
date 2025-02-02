package com.infinity.isbbe.security;

import com.infinity.isbbe.admin.aggregate.Admin;
import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserDetailsService(AdminRepository adminRepository, MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminId(username).orElse(null);
        if (admin != null) {
            return new User(admin.getAdminId(), admin.getAdminPw(), true, true, true, true, admin.getRoles());
        }

        Member member = memberRepository.findByMemberId(username).orElse(null);
        if (member != null) {
            return new User(member.getMemberId(), member.getMemberPw(), true, true, true, true, member.getRoles());
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
