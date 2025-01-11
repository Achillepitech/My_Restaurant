package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.Entree;
import net.achille.myrestaurant.repository.EntreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EntreeService {
    @Autowired
    private EntreeRepository entreeRepository;

    public List<Entree> getAllEntrees() {
        return entreeRepository.findAll();
    }

    public Entree saveEntree(Entree entree) {
        return entreeRepository.save(entree);
    }

    public void deleteEntree(Long id) {
        entreeRepository.deleteById(id);
    }
}