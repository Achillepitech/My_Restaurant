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

    @GetMapping
    public List<Plat> getAllPlats() {
        return platService.getAllPlats();
    }

    // Updated to use String category instead of categoryId
    @GetMapping("/category/{category}")
    public List<Plat> getPlatsByCategory(@PathVariable String category) {
        return platService.getPlatsByCategory(category);
    }

    @GetMapping("/menu-du-jour")
    public List<Plat> getPlatsMenuDuJour() {
        return platService.getPlatsMenuDuJour();
    }

    @PostMapping
    public Plat createPlat(@RequestBody Plat plat) {
        return platService.savePlat(plat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlat(@PathVariable Long id) {
        platService.deletePlat(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plat> updatePlat(@PathVariable Long id, @RequestBody Plat plat) {
        plat.setId(id);
        Plat updatedPlat = platService.savePlat(plat);
        return ResponseEntity.ok(updatedPlat);
    }
}