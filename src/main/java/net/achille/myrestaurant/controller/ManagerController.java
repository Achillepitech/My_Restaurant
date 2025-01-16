package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.*;
import net.achille.myrestaurant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private RestaurantInfoService restaurantInfoService;

    @Autowired
    private EntreeService entreeService;

    @Autowired
    private PlatService platService;

    @Autowired
    private DessertService dessertService;

    @Autowired
    private MenuDuJourService menuDuJourService;

    // ========== CRUD Manager ==========


//    # Créer un manager
//    POST http://localhost:8080/managers
//    Body:
//    {
//        "username": "manager1",
//            "email": "manager1@gmail.com",
//            "password": "12345678"
//    }


    @PostMapping
    public ResponseEntity<User> createManager(@RequestBody User user) {
        User createdManager = managerService.createManager(user);
        return ResponseEntity.ok(createdManager);
    }


    //get managers http://localhost:8080/managers

    @GetMapping
    public ResponseEntity<List<User>> getAllManagers() {
        List<User> managers = managerService.getAllManagers();
        return ResponseEntity.ok(managers);
    }
    // get managers by id GET http://localhost:8080/managers/1
    @GetMapping("/{id}")
    public ResponseEntity<User> getManagerById(@PathVariable Long id) {
        Optional<User> manager = managerService.getManagerById(id);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //http://localhost:8080/managers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> updateManager(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> updatedManager = managerService.updateManager(id, userDetails);
        return updatedManager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE http://localhost:8080/managers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        boolean deleted = managerService.deleteManager(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }







    // ========== Gestion Restaurant ==========

    // Créer un restaurant pour un manager
    @PostMapping("/{managerId}/restaurant")
    public ResponseEntity<RestaurantInfo> createRestaurantForManager(
            @PathVariable Long managerId,
            @RequestBody RestaurantInfo restaurantInfo
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        restaurantInfo.setManager(manager.get());
        RestaurantInfo savedRestaurant = restaurantInfoService.saveOrUpdateRestaurantInfo(restaurantInfo);
        return ResponseEntity.ok(savedRestaurant);
    }

    // Obtenir le restaurant d'un manager
    @GetMapping("/{managerId}/restaurant")
    public ResponseEntity<RestaurantInfo> getManagerRestaurant(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RestaurantInfo restaurant = restaurantInfoService.getRestaurantByManagerId(managerId);
        return ResponseEntity.ok(restaurant);
    }

    // Mettre à jour le restaurant d'un manager
    @PutMapping("/{managerId}/restaurant")
    public ResponseEntity<RestaurantInfo> updateManagerRestaurant(
            @PathVariable Long managerId,
            @RequestBody RestaurantInfo restaurantInfo
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        restaurantInfo.setManager(manager.get());
        RestaurantInfo updatedRestaurant = restaurantInfoService.saveOrUpdateRestaurantInfo(restaurantInfo);
        return ResponseEntity.ok(updatedRestaurant);
    }

    // Supprimer le restaurant d'un manager
    @DeleteMapping("/{managerId}/restaurant")
    public ResponseEntity<Void> deleteManagerRestaurant(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RestaurantInfo restaurant = restaurantInfoService.getRestaurantByManagerId(managerId);
        restaurantInfoService.deleteRestaurant(restaurant.getId());
        return ResponseEntity.noContent().build();
    }



    // ========== Gestion Entrées ==========


    @PostMapping("/{managerId}/restaurant/entrees")
    public ResponseEntity<Entree> addEntree(
            @PathVariable Long managerId,
            @RequestBody Entree entree
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        entree.setManager(manager.get());  // Ajoutez cette ligne
        Entree savedEntree = entreeService.saveEntree(entree);
        return ResponseEntity.ok(savedEntree);
    }

    @GetMapping("/{managerId}/restaurant/entrees")
    public ResponseEntity<List<Entree>> getEntrees(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entreeService.getEntreesByManagerId(managerId));
    }





    // ========== Gestion Plats ==========


    @PostMapping("/{managerId}/restaurant/plats")
    public ResponseEntity<Plat> addPlat(
            @PathVariable Long managerId,
            @RequestBody Plat plat
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        plat.setManager(manager.get());  // Ajout de cette ligne comme pour les entrées
        Plat savedPlat = platService.savePlat(plat);
        return ResponseEntity.ok(savedPlat);
    }

    @GetMapping("/{managerId}/restaurant/plats")
    public ResponseEntity<List<Plat>> getPlats(@PathVariable Long managerId) {  // Ajout du paramètre
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(platService.getPlatsByManagerId(managerId));  // Utilisation de la nouvelle méthode
    }

    @PutMapping("/{managerId}/restaurant/plats/{platId}")  // Ajout de cet endpoint
    public ResponseEntity<Plat> updatePlat(
            @PathVariable Long managerId,
            @PathVariable Long platId,
            @RequestBody Plat plat
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        plat.setId(platId);
        plat.setManager(manager.get());
        Plat updatedPlat = platService.savePlat(plat);
        return ResponseEntity.ok(updatedPlat);
    }


    @DeleteMapping("/{managerId}/restaurant/plats/{platId}")
    public ResponseEntity<Void> deletePlat(
            @PathVariable Long managerId,
            @PathVariable Long platId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        platService.deletePlat(platId);
        return ResponseEntity.noContent().build();
    }


    // ========== Gestion Desserts ==========



    @PostMapping("/{managerId}/restaurant/desserts")
    public ResponseEntity<Dessert> addDessert(
            @PathVariable Long managerId,
            @RequestBody Dessert dessert
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dessert.setManager(manager.get());
        Dessert savedDessert = dessertService.saveDessert(dessert);
        return ResponseEntity.ok(savedDessert);
    }

    @GetMapping("/{managerId}/restaurant/desserts")
    public ResponseEntity<List<Dessert>> getDesserts(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dessertService.getDessertsByManagerId(managerId));
    }

    @PutMapping("/{managerId}/restaurant/desserts/{dessertId}")  // Ajouter cet endpoint
    public ResponseEntity<Dessert> updateDessert(
            @PathVariable Long managerId,
            @PathVariable Long dessertId,
            @RequestBody Dessert dessert
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dessert.setId(dessertId);
        dessert.setManager(manager.get());
        Dessert updatedDessert = dessertService.saveDessert(dessert);
        return ResponseEntity.ok(updatedDessert);
    }

    @DeleteMapping("/{managerId}/restaurant/desserts/{dessertId}")
    public ResponseEntity<Void> deleteDessert(
            @PathVariable Long managerId,
            @PathVariable Long dessertId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dessertService.deleteDessert(dessertId);
        return ResponseEntity.noContent().build();
    }


    // ========== Gestion Menus ==========




    @PostMapping("/{managerId}/restaurant/menus")
    public ResponseEntity<MenuDuJour> createMenu(
            @PathVariable Long managerId,
            @RequestBody MenuDuJour menu
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menu.setManager(manager.get());
        MenuDuJour savedMenu = menuDuJourService.createMenu(menu);
        return ResponseEntity.ok(savedMenu);
    }

    @GetMapping("/{managerId}/restaurant/menus")
    public ResponseEntity<List<MenuDuJour>> getAllMenus(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(menuDuJourService.getMenusByManagerId(managerId));
    }

    @GetMapping("/{managerId}/restaurant/menus/{menuId}")
    public ResponseEntity<MenuDuJour> getMenuById(
            @PathVariable Long managerId,
            @PathVariable Long menuId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MenuDuJour menu = menuDuJourService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/{managerId}/restaurant/menus/{menuId}")
    public ResponseEntity<MenuDuJour> updateMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @RequestBody MenuDuJour menu
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menu.setId(menuId);
        menu.setManager(manager.get());
        MenuDuJour updatedMenu = menuDuJourService.createMenu(menu);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{managerId}/restaurant/menus/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menuDuJourService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }


}


//