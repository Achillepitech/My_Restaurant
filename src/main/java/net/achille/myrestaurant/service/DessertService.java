package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.Dessert;
import net.achille.myrestaurant.repository.DessertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DessertService {
    @Autowired
    private DessertRepository dessertRepository;

    public List<Dessert> getAllDesserts() {
        return dessertRepository.findAll();
    }

    public List<Dessert> getDessertsByManagerId(Long managerId) {
        return dessertRepository.findByManagerId(managerId);
    }

    public Dessert saveDessert(Dessert dessert) {
        return dessertRepository.save(dessert);
    }

    public void deleteDessert(Long id) {
        dessertRepository.deleteById(id);
    }
}