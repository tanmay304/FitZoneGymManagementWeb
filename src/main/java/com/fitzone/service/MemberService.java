package com.fitzone.service;

import com.fitzone.model.Member;
import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();
    List<Member> searchMembers(String keyword);
    Member getMemberById(int id);
    boolean addMember(Member member);
    boolean updateMember(Member member);
    boolean deleteMember(int id);
    int getTotalMembersCount();
    boolean isEmailDuplicate(String email, int excludeId);
    boolean isMobileDuplicate(String mobile, int excludeId);
}
