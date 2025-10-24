package com.springboot;

import com.springboot.domain.Address;
import com.springboot.domain.Member;
import com.springboot.domain.Role;
import com.springboot.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.*;

@EnableJpaAuditing
@SpringBootApplication
public class BookmarketApplication {

	public static void main(String[] args) {
//		주석 단축키   ctrl +  /
		SpringApplication.run(BookmarketApplication.class, args);

//		SpringApplication app = new SpringApplication(BookmarketApplication.class);
//		app.run(args);


	}

//	시작될 때 자동 실행되는 코드”예요. 즉,서버가 부팅된 직후, DB에 관리자 계정을 자동으로 한 번 등록하는 코드입니다.//관리자 계정(Admin)을 DB에 자동으로 생성해두려는 목적/DB에 Admin 계정이 없으면 로그인 자체가 안 되니까
	@Bean
	public CommandLineRunner run(MemberService memberService) throws Exception {
		return (String[] args) -> {
			try {
			Address address = new Address();
			address.setAddressname("서울시");

			Member member = new Member();
			member.setMemberId("Admin");
			member.setName("관리자");
			member.setPhone("");
			member.setEmail("");
			member.setAddress(address);
			String password = new BCryptPasswordEncoder().encode("Admin1234");
			member.setPassword(password);
			member.setRole(Role.ADMIN);
			memberService.saveMember(member);
			} catch (IllegalStateException e) {
				System.out.println("관리자 계정이 이미 존재합니다. 생성을 건너뜁니다.");
			}
		};
	}



}
