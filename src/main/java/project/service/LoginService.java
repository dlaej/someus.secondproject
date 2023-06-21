package project.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import project.dto.LoginDto;
import project.dto.MemberDto;

public interface LoginService extends UserDetailsService {
	public MemberDto login(LoginDto loginDto) throws Exception;
	public int registUser(MemberDto memberDto) throws Exception;
	public MemberDto selectUserByUserId(String membername) throws Exception;
}
