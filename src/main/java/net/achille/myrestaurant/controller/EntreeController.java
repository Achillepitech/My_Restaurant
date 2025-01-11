package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.Entree;
import net.achille.myrestaurant.service.EntreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrees")
@CrossOrigin("*")
public class EntreeController {

    @Autowired
    private EntreeService entreeService;

    @GetMapping
    public List<Entree> getAllEntrees() {
        return entreeService.getAllEntrees();
    }

    @PostMapping
    public Entree createEntree(@RequestBody Entree entree) {
        return entreeService.saveEntree(entree);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entree> updateEntree(@PathVariable Long id, @RequestBody Entree entree) {
        entree.setId(id);
        Entree updatedEntree = entreeService.saveEntree(entree);
        return ResponseEntity.ok(updatedEntree);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntree(@PathVariable Long id) {
        entreeService.deleteEntree(id);
        return ResponseEntity.ok().build();
    }
}

//GET /api/entrees : Liste toutes les entrées
//POST /api/entrees : Crée une nouvelle entrée
//PUT /api/entrees/{id} : Met à jour une entrée
//DELETE /api/entrees/{id} : Supprime une entrée