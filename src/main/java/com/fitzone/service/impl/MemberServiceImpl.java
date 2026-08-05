package com.fitzone.service.impl;

import com.fitzone.dao.MemberDAO;
import com.fitzone.dao.impl.MemberDAOImpl;
import com.fitzone.model.Member;
import com.fitzone.service.MemberService;
import java.util.List;

public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;

    public MemberServiceImpl() {
        this.memberDAO = new MemberDAOImpl();
    }

    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    @Override
    public List<Member> searchMembers(String keyword) {
        return memberDAO.searchMembers(keyword);
    }

    @Override
    public Member getMemberById(int id) {
        return memberDAO.getMemberById(id);
    }

    @Override
    public boolean addMember(Member member) {
        return memberDAO.addMember(member);
    }

    @Override
    public boolean updateMember(Member member) {
        return memberDAO.updateMember(member);
    }

    @Override
    public boolean deleteMember(int id) {
        return memberDAO.deleteMember(id);
    }

    @Override
    public int getTotalMembersCount() {
        return memberDAO.getTotalMembersCount();
    }

    @Override
    public boolean isEmailDuplicate(String email, int excludeId) {
        return memberDAO.existsByEmail(email, excludeId);
    }

    @Override
    public boolean isMobileDuplicate(String mobile, int excludeId) {
        return memberDAO.existsByMobile(mobile, excludeId);
    }
}
