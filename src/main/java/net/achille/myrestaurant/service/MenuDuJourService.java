//package net.achille.myrestaurant.service;
//
//import net.achille.myrestaurant.model.Entree;
//import net.achille.myrestaurant.model.MenuDuJour;
//import net.achille.myrestaurant.model.MenuDuJourNew;
//import net.achille.myrestaurant.repository.EntreeRepository;
//import net.achille.myrestaurant.repository.MenuDuJourRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class MenuDuJourService {
//    @Autowired
//    private MenuDuJourRepository menuDuJourRepository;
//
//    public List<MenuDuJour> getAllMenus() {
//        return menuDuJourRepository.findAll();
//
//    }
//
//    public List<MenuDuJour> getMenusByManagerId(Long managerId) {  // Ajouter cette méthode
//        return menuDuJourRepository.findByManagerId(managerId);
//    }
//
//    public MenuDuJour getMenuById(Long id) {
//        return menuDuJourRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Menu non trouvé avec l'id: " + id));
//    }
//
//    public MenuDuJour createMenu(MenuDuJour menu) {
//        menu.setActif(true);
//        return menuDuJourRepository.save(menu);
//    }
//
//    public void deleteMenu(Long id) {
//        menuDuJourRepository.deleteById(id);
//    }
//
//
//    @Autowired
//    private EntreeRepository entreeRepository;
//
//    public MenuDuJourNew addEntreeToMenu(Long menuId, Long entreeId) {
//        MenuDuJour menu = menuDuJourRepository.findById(menuId)
//                .orElseThrow(() -> new RuntimeException("Menu non trouvé avec l'id: " + menuId));
//
//        Entree entree = entreeRepository.findById(entreeId)
//                .orElseThrow(() -> new RuntimeException("Entrée non trouvée avec l'id: " + entreeId));
//
//        if (menu.getEntrees() == null) {
//            menu.setEntrees(new ArrayList<>());
//        }
//        menu.getEntrees().add(entree);
//        return menuDuJourRepository.save(menu);
//    }
//
//    public MenuDuJourNew removeEntreeFromMenu(Long menuId, Long entreeId) {
//        MenuDuJour menu = menuDuJourRepository.findById(menuId)
//                .orElseThrow(() -> new RuntimeException("Menu non trouvé avec l'id: " + menuId));
//
//        if (menu.getEntrees() == null || menu.getEntrees().isEmpty()) {
//            throw new RuntimeException("Aucune entrée dans le menu à supprimer");
//        }
//
//        boolean removed = menu.getEntrees().removeIf(entree -> entree.getId().equals(entreeId));
//        if (!removed) {
//            throw new RuntimeException("Entrée non trouvée dans le menu");
//        }
//
//        return menuDuJourRepository.save(menu);
//    }
//
//
//}