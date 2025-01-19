package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.Dessert;
import net.achille.myrestaurant.model.MenuDuJourNew;
import net.achille.myrestaurant.repository.DessertRepository;
import net.achille.myrestaurant.repository.MenuDuJourNewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DessertService {
    @Autowired
    private DessertRepository dessertRepository;

    @Autowired
    private MenuDuJourNewRepository menuRepository;

    // Méthodes CRUD de base
    public Dessert saveDessert(Dessert dessert) {
        return dessertRepository.save(dessert);
    }

    public Dessert getDessertById(Long dessertId) {
        return dessertRepository.findById(dessertId)
                .orElseThrow(() -> new RuntimeException("Dessert non trouvé avec l'ID: " + dessertId));
    }

    public List<Dessert> getAllDesserts() {
        return dessertRepository.findAll();
    }

    public List<Dessert> getDessertsByManagerId(Long managerId) {
        return dessertRepository.findByManagerId(managerId);
    }

    public Dessert updateDessert(Long dessertId, Dessert updatedDessert) {
        Dessert existingDessert = getDessertById(dessertId);

        existingDessert.setNom(updatedDessert.getNom());
        existingDessert.setDescription(updatedDessert.getDescription());
        existingDessert.setPrix(updatedDessert.getPrix());

        return dessertRepository.save(existingDessert);
    }

    public void deleteDessert(Long dessertId) {
        // Vérifie d'abord si le dessert existe
        getDessertById(dessertId);
        dessertRepository.deleteById(dessertId);
    }

    // Méthodes de gestion Menu-Dessert
    public MenuDuJourNew addDessertToMenu(Long menuId, Long dessertId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu non trouvé avec l'ID: " + menuId));

        Dessert dessert = getDessertById(dessertId);

        menu.getDesserts().add(dessert);
        return menuRepository.save(menu);
    }

    public MenuDuJourNew removeDessertFromMenu(Long menuId, Long dessertId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu non trouvé avec l'ID: " + menuId));

        menu.getDesserts().removeIf(dessert -> dessert.getId().equals(dessertId));
        return menuRepository.save(menu);
    }
}