package project.mapper;

import org.apache.ibatis.annotations.Mapper;

import project.dto.LoginDto;
import project.dto.MemberDto;

@Mapper
public interface LoginMapper {
	public MemberDto login(LoginDto loginDto) throws Exception;
	public int registUser(MemberDto memberDto) throws Exception;
	public MemberDto selectUserByUserId(String membername);
}

