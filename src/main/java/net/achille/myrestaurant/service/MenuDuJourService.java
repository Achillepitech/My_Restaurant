package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.MenuDuJour;
import net.achille.myrestaurant.repository.MenuDuJourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class MenuDuJourService {

    @Autowired
    private MenuDuJourRepository menuDuJourRepository;

    public MenuDuJour saveMenuDuJour(MenuDuJour menuDuJour) {
        if (menuDuJour.getDate() == null) {
            menuDuJour.setDate(LocalDate.now());
        }
        return menuDuJourRepository.save(menuDuJour);
    }

    public MenuDuJour getMenuDuJour() {
        return menuDuJourRepository.findByDateAndActifTrue(LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Aucun menu du jour disponible"));
    }

    public Optional<MenuDuJour> getLatestMenuDuJour() {
        return menuDuJourRepository.findTopByOrderByDateDesc();
    }

    public void deleteMenuDuJour(Long id) {
        menuDuJourRepository.deleteById(id);
    }

    public Optional<MenuDuJour> getMenuDuJour(LocalDate date) {
        return menuDuJourRepository.findByDateAndActifTrue(date);
    }
}