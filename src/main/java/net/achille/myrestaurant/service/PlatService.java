package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.Plat;
import net.achille.myrestaurant.repository.PlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlatService {
    @Autowired
    private PlatRepository platRepository;

    public List<Plat> getAllPlats() {
        return platRepository.findAll();
    }

    public List<Plat> getPlatsByCategory(String category) {
        return platRepository.findByCategory(category);
    }

    public List<Plat> getPlatsMenuDuJour() {
        return platRepository.findByMenuDuJour(true);
    }

    public Plat savePlat(Plat plat) {
        return platRepository.save(plat);
    }

    public void deletePlat(Long id) {
        platRepository.deleteById(id);
    }
}