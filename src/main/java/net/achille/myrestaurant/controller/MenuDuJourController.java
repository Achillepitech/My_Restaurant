package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.MenuDuJour;
import net.achille.myrestaurant.service.MenuDuJourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin("*")
public class MenuDuJourController {
    @Autowired
    private MenuDuJourService menuDuJourService;

    // Obtenir tous les menus
    @GetMapping
    public ResponseEntity<List<MenuDuJour>> getAllMenus() {
        return ResponseEntity.ok(menuDuJourService.getAllMenus());
    }

    // Obtenir un menu spécifique
    @GetMapping("/{id}")
    public ResponseEntity<MenuDuJour> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuDuJourService.getMenuById(id));
    }

    // Créer un nouveau menu
    @PostMapping
    public ResponseEntity<MenuDuJour> createMenu(@RequestBody MenuDuJour menu) {
        return ResponseEntity.ok(menuDuJourService.createMenu(menu));
    }

    // Supprimer un menu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuDuJourService.deleteMenu(id);
        return ResponseEntity.ok().build();
    }
}

