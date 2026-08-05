package com.fitzone.dao;

import com.fitzone.model.Trainer;
import java.util.List;

public interface TrainerDAO {
    List<Trainer> getAllTrainers();
    boolean addTrainer(Trainer trainer);
    boolean updateTrainer(Trainer trainer);
    boolean deleteTrainer(int id);
    int getTrainerCount();
}
