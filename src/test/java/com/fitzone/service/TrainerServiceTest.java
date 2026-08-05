package com.fitzone.service;

import com.fitzone.dao.TrainerDAO;
import com.fitzone.model.Trainer;
import com.fitzone.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTrainers() {
        Trainer t = new Trainer();
        t.setId(10);
        t.setName("Trainer Bob");

        when(trainerDAO.getAllTrainers()).thenReturn(Collections.singletonList(t));

        List<Trainer> trainers = trainerService.getAllTrainers();
        assertEquals(1, trainers.size());
        assertEquals("Trainer Bob", trainers.get(0).getName());
    }

    @Test
    public void testAddTrainer() {
        Trainer t = new Trainer();
        t.setName("Coach Sarah");
        t.setSpecialty("CrossFit");

        when(trainerDAO.addTrainer(t)).thenReturn(true);

        assertTrue(trainerService.addTrainer(t));
        verify(trainerDAO, times(1)).addTrainer(t);
    }

    @Test
    public void testDeleteTrainer() {
        when(trainerDAO.deleteTrainer(5)).thenReturn(true);

        assertTrue(trainerService.deleteTrainer(5));
        verify(trainerDAO, times(1)).deleteTrainer(5);
    }
}
