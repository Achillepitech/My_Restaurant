package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.MenuDuJour;
import net.achille.myrestaurant.service.MenuDuJourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuDuJourController {

    @Autowired
    private MenuDuJourService menuDuJourService;

    // Récupérer le menu du jour actuel
    // GET http://localhost:8080/api/menu/today
    @GetMapping("/today")
    public ResponseEntity<MenuDuJour> getMenuDuJour() {
        return ResponseEntity.ok(menuDuJourService.getMenuDuJour());
    }

    // Créer un nouveau menu du jour
    // POST http://localhost:8080/api/menu
    // Body:
    // {
    //    "date": "2025-01-08",
    //    "entree": {
    //        "id": 2
    //    },
    //    "platPrincipal": {
    //        "id": 1
    //    },
    //    "dessert": {
    //        "id": 5
    //    },
    //    "prix": 29.90
    // }
    @PostMapping
    public ResponseEntity<MenuDuJour> createMenu(@RequestBody MenuDuJour menuDuJour) {
        return ResponseEntity.ok(menuDuJourService.saveMenuDuJour(menuDuJour));
    }

    // Mettre à jour un menu existant
    // PUT http://localhost:8080/api/menu/1
    @PutMapping("/{id}")
    public ResponseEntity<MenuDuJour> updateMenu(@PathVariable Long id, @RequestBody MenuDuJour menuDuJour) {
        menuDuJour.setId(id);
        return ResponseEntity.ok(menuDuJourService.saveMenuDuJour(menuDuJour));
    }

    // Supprimer un menu
    // DELETE http://localhost:8080/api/menu/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        menuDuJourService.deleteMenuDuJour(id);
        return ResponseEntity.ok().build();
    }

    // Récupérer le dernier menu créé
    // GET http://localhost:8080/api/menu/latest
    @GetMapping("/latest")
    public ResponseEntity<MenuDuJour> getLatestMenu() {
        return menuDuJourService.getLatestMenuDuJour()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}