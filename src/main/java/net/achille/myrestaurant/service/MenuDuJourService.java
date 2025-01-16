package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.MenuDuJour;
import net.achille.myrestaurant.repository.MenuDuJourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuDuJourService {
    @Autowired
    private MenuDuJourRepository menuDuJourRepository;

    public List<MenuDuJour> getAllMenus() {
        return menuDuJourRepository.findAll();

    }

    public List<MenuDuJour> getMenusByManagerId(Long managerId) {  // Ajouter cette méthode
        return menuDuJourRepository.findByManagerId(managerId);
    }

    public MenuDuJour getMenuById(Long id) {
        return menuDuJourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu non trouvé avec l'id: " + id));
    }

    public MenuDuJour createMenu(MenuDuJour menu) {
        menu.setActif(true);
        return menuDuJourRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        menuDuJourRepository.deleteById(id);
    }
}