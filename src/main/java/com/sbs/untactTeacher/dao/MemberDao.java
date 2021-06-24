package com.sbs.untactTeacher.dao;

import com.sbs.untactTeacher.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDao {

    Member getMemberById(@Param("id") int id);

    void join(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name, 
    		@Param("nickname") String nickname, @Param("cellphoneNo") String cellphoneNo, @Param("email") String email);
    
    Member getMemberByLoginId(@Param("loginId") String loginId);

    Member getMemberByNameAndEmail(@Param("name") String name, @Param("email") String email);
    
    void modify(@Param("id") int id, @Param("loginPw") String loginPw, @Param("name") String name, @Param("nickname") String nickname, @Param("cellphoneNo") String cellphoneNo, @Param("email") String email);

    int getLastInsertId();


    List<Member> getForPrintMembers();
}
