package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.Dessert;
import net.achille.myrestaurant.service.DessertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desserts")
@CrossOrigin("*")
public class DessertController {

    @Autowired
    private DessertService dessertService;

    @GetMapping
    public List<Dessert> getAllDesserts() {
        return dessertService.getAllDesserts();
    }

    @PostMapping
    public Dessert createDessert(@RequestBody Dessert dessert) {
        return dessertService.saveDessert(dessert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dessert> updateDessert(@PathVariable Long id, @RequestBody Dessert dessert) {
        dessert.setId(id);
        Dessert updatedDessert = dessertService.saveDessert(dessert);
        return ResponseEntity.ok(updatedDessert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDessert(@PathVariable Long id) {
        dessertService.deleteDessert(id);
        return ResponseEntity.ok().build();
    }
}