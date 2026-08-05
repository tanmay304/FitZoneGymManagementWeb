package com.fitzone.dao;

import com.fitzone.model.Member;
import java.util.List;

public interface MemberDAO {
    List<Member> getAllMembers();
    List<Member> searchMembers(String keyword);
    Member getMemberById(int id);
    Member getMemberByEmail(String email);
    Member getMemberByMobile(String mobile);
    boolean existsByEmail(String email, int excludeId);
    boolean existsByMobile(String mobile, int excludeId);
    boolean addMember(Member member);
    boolean updateMember(Member member);
    boolean deleteMember(int id);
    int getTotalMembersCount();
}
