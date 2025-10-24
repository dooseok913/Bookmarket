package com.springboot.service;

import com.springboot.domain.Member;
import com.springboot.domain.MemberFormDto;
import com.springboot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public void updateMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member existingMember = memberRepository.findByMemberId(memberFormDto.getMemberId());
        if(existingMember == null) {
            throw new IllegalStateException("해당 회원 찾을 수 없습니다.");

        }
        existingMember.setMemberId(memberFormDto.getMemberId());
        existingMember.setPassword(memberFormDto.getPassword());
        existingMember.setEmail(memberFormDto.getEmail());
        existingMember.setPhone(memberFormDto.getPhone());
        existingMember.setAddress(memberFormDto.getAddress());

        if (!memberFormDto.getPassword().isEmpty()) {
            existingMember.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));
        }
        memberRepository.save(existingMember);
    }

    public Member getMemberById(String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        return member;
    }

    public void deleteMember(String memberId) {
        Member member = memberRepository.findByMemberId(memberId);
        memberRepository.deleteById(member.getNum());

    }

    private  void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByMemberId(member.getMemberId());
        if (findMember != null) {
            throw  new IllegalStateException("이미 가입된 회원입니다.");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
        Member member = memberRepository.findByMemberId(id);
        if(member == null) {
            throw new UsernameNotFoundException(id);
        }
        return User.builder()
                .username(member.getMemberId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }


}
