package com.fitzone.service;

import com.fitzone.dao.MemberDAO;
import com.fitzone.model.Member;
import com.fitzone.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberDAO memberDAO;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMembers() {
        Member m1 = new Member();
        m1.setId(1);
        m1.setFname("John");
        Member m2 = new Member();
        m2.setId(2);
        m2.setFname("Jane");

        when(memberDAO.getAllMembers()).thenReturn(Arrays.asList(m1, m2));

        List<Member> result = memberService.getAllMembers();
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFname());
        verify(memberDAO, times(1)).getAllMembers();
    }

    @Test
    public void testAddMember() {
        Member m = new Member();
        m.setFname("Alex");
        m.setEmail("alex@test.com");

        when(memberDAO.addMember(m)).thenReturn(true);

        boolean success = memberService.addMember(m);
        assertTrue(success);
        verify(memberDAO, times(1)).addMember(m);
    }

    @Test
    public void testIsEmailDuplicate() {
        when(memberDAO.existsByEmail("test@fitzone.com", 1)).thenReturn(true);

        assertTrue(memberService.isEmailDuplicate("test@fitzone.com", 1));
        verify(memberDAO, times(1)).existsByEmail("test@fitzone.com", 1);
    }

    @Test
    public void testGetTotalMembersCount() {
        when(memberDAO.getTotalMembersCount()).thenReturn(45);

        assertEquals(45, memberService.getTotalMembersCount());
        verify(memberDAO, times(1)).getTotalMembersCount();
    }
}
