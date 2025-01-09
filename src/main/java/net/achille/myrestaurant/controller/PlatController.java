package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.Plat;
import net.achille.myrestaurant.service.PlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plats")
@CrossOrigin("*")
public class PlatController {

    @Autowired
    private PlatService platService;

    // Récupérer tous les plats
    @GetMapping
    public List<Plat> getAllPlats() {
        return platService.getAllPlats();
    }

    // Récupérer les plats par catégorie
    @GetMapping("/category/{categoryId}")
    public List<Plat> getPlatsByCategory(@PathVariable Long categoryId) {
        return platService.getPlatsByCategory(categoryId);
    }

    // Récupérer les plats du menu du jour
    @GetMapping("/menu-du-jour")
    public List<Plat> getPlatsMenuDuJour() {
        return platService.getPlatsMenuDuJour();
    }

    // Créer un nouveau plat
    @PostMapping
    public Plat createPlat(@RequestBody Plat plat) {
        return platService.savePlat(plat);
    }

    // Supprimer un plat
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlat(@PathVariable Long id) {
        platService.deletePlat(id);
        return ResponseEntity.ok().build();
    }

    // Mettre à jour un plat
    @PutMapping("/{id}")
    public ResponseEntity<Plat> updatePlat(@PathVariable Long id, @RequestBody Plat plat) {
        plat.setId(id);
        Plat updatedPlat = platService.savePlat(plat);
        return ResponseEntity.ok(updatedPlat);
    }
}