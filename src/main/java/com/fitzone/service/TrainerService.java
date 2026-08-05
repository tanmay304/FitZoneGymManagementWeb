package com.fitzone.service;

import com.fitzone.model.Trainer;
import java.util.List;

public interface TrainerService {
    List<Trainer> getAllTrainers();
    boolean addTrainer(Trainer trainer);
    boolean updateTrainer(Trainer trainer);
    boolean deleteTrainer(int id);
    int getTrainerCount();
}
