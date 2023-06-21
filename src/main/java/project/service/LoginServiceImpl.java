package project.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.dto.LoginDto;
import project.dto.MemberDto;
import project.mapper.LoginMapper;

@Service 
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public MemberDto login(LoginDto loginDto) throws Exception {
		return loginMapper.login(loginDto);
	}

	@Override
	public int registUser(MemberDto memberDto) throws Exception {
		// 패스워드를 암호화해서 새로 저장
		memberDto.setMemberPw(passwordEncoder.encode(memberDto.getMemberPw()));
		return loginMapper.registUser(memberDto);
	}

	@Override
	public UserDetails loadUserByUsername(String membername) throws UsernameNotFoundException {
		MemberDto memberDto = loginMapper.selectUserByUserId(membername);
		if (memberDto == null) {
			throw new UsernameNotFoundException(membername);
		}
		
		return new User(memberDto.getMemberId(), memberDto.getMemberPw(), 
				true, true, true, true, new ArrayList<>());		
	}

	@Override
	public MemberDto selectUserByUserId(String membername) throws Exception {
		// TODO Auto-generated method stub
		return loginMapper.selectUserByUserId(membername);
	}
	
}
