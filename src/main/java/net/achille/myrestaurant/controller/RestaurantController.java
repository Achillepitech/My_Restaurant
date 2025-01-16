package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.RestaurantInfo;
import net.achille.myrestaurant.service.RestaurantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin("*")
public class RestaurantController {

    @Autowired
    private RestaurantInfoService restaurantInfoService;

    // Obtenir tous les restaurants
    @GetMapping
    public ResponseEntity<List<RestaurantInfo>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantInfoService.getAllRestaurants());
    }

    // Obtenir un restaurant par ID
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantInfo> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantInfoService.getRestaurantById(id));
    }

    // Créer un nouveau restaurant
    @PostMapping
    public ResponseEntity<RestaurantInfo> createRestaurant(@RequestBody RestaurantInfo restaurant) {
        return ResponseEntity.ok(restaurantInfoService.saveOrUpdateRestaurantInfo(restaurant));
    }

    // Mettre à jour un restaurant
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantInfo> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantInfo restaurant) {
        restaurant.setId(id);
        return ResponseEntity.ok(restaurantInfoService.saveOrUpdateRestaurantInfo(restaurant));
    }

    // Supprimer un restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantInfoService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    // Obtenir le restaurant par ID du manager
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<RestaurantInfo> getRestaurantByManagerId(@PathVariable Long managerId) {
        return ResponseEntity.ok(restaurantInfoService.getRestaurantByManagerId(managerId));
    }
}