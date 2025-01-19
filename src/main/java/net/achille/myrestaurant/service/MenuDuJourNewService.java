package net.achille.myrestaurant.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.achille.myrestaurant.model.Entree;
import net.achille.myrestaurant.model.MenuDuJourNew;
import net.achille.myrestaurant.repository.EntreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import net.achille.myrestaurant.model.Dessert;
import net.achille.myrestaurant.repository.DessertRepository;

import net.achille.myrestaurant.model.Plat;
import net.achille.myrestaurant.repository.MenuDuJourNewRepository;
import net.achille.myrestaurant.repository.PlatRepository;
import net.achille.myrestaurant.repository.DessertRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MenuDuJourNewService {

    @Autowired
    private MenuDuJourNewRepository menuRepository;


    public MenuDuJourNew saveMenu(MenuDuJourNew menu) {
        return menuRepository.save(menu);
    }

    public List<MenuDuJourNew> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<MenuDuJourNew> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    public List<MenuDuJourNew> getMenusByManagerId(Long managerId) {
        return menuRepository.findByManagerId(managerId);
    }

    @Autowired

    private PlatRepository platRepository;

    public MenuDuJourNew addPlatToMenu(Long menuId, Long platId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        Plat plat = platRepository.findById(platId)
                .orElseThrow(() -> new RuntimeException("Plat not found"));

        if (menu.getPlats() == null) {
            menu.setPlats(new ArrayList<>());
        }

        menu.getPlats().add(plat);
        return menuRepository.save(menu);
    }


    public MenuDuJourNew removePlatFromMenu(Long menuId, Long platId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        if (menu.getPlats() == null || menu.getPlats().isEmpty()) {
            throw new RuntimeException("No plats in the menu to remove");
        }

        Plat platToRemove = platRepository.findById(platId)
                .orElseThrow(() -> new RuntimeException("Plat not found"));

        boolean removed = menu.getPlats().removeIf(plat -> plat.getId().equals(platToRemove.getId()));

        if (!removed) {
            throw new RuntimeException("Plat not found in the menu");
        }

        return menuRepository.save(menu);

    }


    @Autowired
    private EntreeRepository entreeRepository;

    @Transactional
    public MenuDuJourNew addEntreeToMenu(Long menuId, Long entreeId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        Entree entree = entreeRepository.findById(entreeId)
                .orElseThrow(() -> new RuntimeException("Entree not found"));

        if (menu.getEntrees() == null) {
            menu.setEntrees(new ArrayList<>());
        }
        menu.getEntrees().add(entree);

        return menuRepository.save(menu);
    }

    @Transactional
    public MenuDuJourNew removeEntreeFromMenu(Long menuId, Long entreeId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        menu.getEntrees().removeIf(entree -> entree.getId().equals(entreeId));

        return menuRepository.save(menu);
    }


    // Gestion des Desserts

    @Autowired
    private DessertRepository dessertRepository;


    @Transactional
    public MenuDuJourNew addDessertToMenu(Long menuId, Long dessertId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        Dessert dessert = dessertRepository.findById(dessertId)
                .orElseThrow(() -> new RuntimeException("Dessert not found"));

        if (menu.getDesserts() == null) {
            menu.setDesserts(new ArrayList<>());
        }

        // Vérifier si le dessert n'est pas déjà dans le menu
        if (menu.getDesserts().stream().anyMatch(d -> d.getId().equals(dessert.getId()))) {
            throw new RuntimeException("Dessert already exists in the menu");
        }

        menu.getDesserts().add(dessert);
        return menuRepository.save(menu);
    }

    @Transactional
    public MenuDuJourNew removeDessertFromMenu(Long menuId, Long dessertId) {
        MenuDuJourNew menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        if (menu.getDesserts() == null || menu.getDesserts().isEmpty()) {
            throw new RuntimeException("No desserts in the menu to remove");
        }

        boolean removed = menu.getDesserts().removeIf(dessert -> dessert.getId().equals(dessertId));

        if (!removed) {
            throw new RuntimeException("Dessert not found in the menu");
        }

        return menuRepository.save(menu);
    }

}

