package com.fitzone.service.impl;

import com.fitzone.dao.TrainerDAO;
import com.fitzone.dao.impl.TrainerDAOImpl;
import com.fitzone.model.Trainer;
import com.fitzone.service.TrainerService;
import java.util.List;

public class TrainerServiceImpl implements TrainerService {
    private final TrainerDAO trainerDAO;

    public TrainerServiceImpl() {
        this.trainerDAO = new TrainerDAOImpl();
    }

    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.getAllTrainers();
    }

    @Override
    public boolean addTrainer(Trainer trainer) {
        return trainerDAO.addTrainer(trainer);
    }

    @Override
    public boolean updateTrainer(Trainer trainer) {
        return trainerDAO.updateTrainer(trainer);
    }

    @Override
    public boolean deleteTrainer(int id) {
        return trainerDAO.deleteTrainer(id);
    }

    @Override
    public int getTrainerCount() {
        return trainerDAO.getTrainerCount();
    }
}
