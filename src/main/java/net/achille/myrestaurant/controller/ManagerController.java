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
    //Créer un restaurant pour un manager
    //POST http://localhost:8080/managers/{id}/restaurant

    //  "name": "Mon Restaurant",
    //    "address": "123 rue Example",
    //    "phoneNumber": "0123456789"
    //}

    @PostMapping("/{managerId}/restaurant")
    public ResponseEntity<RestaurantInfo> createRestaurantForManager(
            @PathVariable Long managerId,
            @RequestBody RestaurantInfo restaurantInfo
    ) {
        Optional<User> optionalManager = managerService.getManagerById(managerId);
        if (optionalManager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User manager = optionalManager.get();

        // Si le manager a déjà un restaurant, on le supprime d'abord
        if (manager.getManagedRestaurant() != null) {
            restaurantInfoService.deleteRestaurant(manager.getManagedRestaurant().getId());
            manager.setManagedRestaurant(null);
        }

        restaurantInfo.setManager(manager);

        // Sauvegarder le restaurant
        RestaurantInfo savedRestaurant = restaurantInfoService.saveOrUpdateRestaurantInfo(restaurantInfo);

        // Mettre à jour le manager
        manager.setManagedRestaurant(savedRestaurant);
        managerService.updateManager(managerId, manager);

        return ResponseEntity.ok(savedRestaurant);
    }







    @GetMapping("/{managerId}/restaurant")
    public ResponseEntity<RestaurantInfo> getManagerRestaurant(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(manager.get().getManagedRestaurant());
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

        Entree savedEntree = entreeService.saveEntree(entree);
        return ResponseEntity.ok(savedEntree);
    }

    @GetMapping("/{managerId}/restaurant/entrees")
    public ResponseEntity<List<Entree>> getEntrees() {
        return ResponseEntity.ok(entreeService.getAllEntrees());
    }

    @DeleteMapping("/{managerId}/restaurant/entrees/{entreeId}")
    public ResponseEntity<Void> deleteEntree(
            @PathVariable Long managerId,
            @PathVariable Long entreeId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        entreeService.deleteEntree(entreeId);
        return ResponseEntity.noContent().build();
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

        Plat savedPlat = platService.savePlat(plat);
        return ResponseEntity.ok(savedPlat);
    }

    @GetMapping("/{managerId}/restaurant/plats")
    public ResponseEntity<List<Plat>> getPlats() {
        return ResponseEntity.ok(platService.getAllPlats());
    }

    @GetMapping("/{managerId}/restaurant/plats/menu-du-jour")
    public ResponseEntity<List<Plat>> getPlatsMenuDuJour() {
        return ResponseEntity.ok(platService.getPlatsMenuDuJour());
    }

    @GetMapping("/{managerId}/restaurant/plats/{category}")
    public ResponseEntity<List<Plat>> getPlatsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(platService.getPlatsByCategory(category));
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

        Dessert savedDessert = dessertService.saveDessert(dessert);
        return ResponseEntity.ok(savedDessert);
    }

    @GetMapping("/{managerId}/restaurant/desserts")
    public ResponseEntity<List<Dessert>> getDesserts() {
        return ResponseEntity.ok(dessertService.getAllDesserts());
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

    // CREATE - Créer un menu
    //  { "nom": "Menu du Jour",
    //    "description": "Entrée + Plat + Dessert",
    //    "prix": 25.90,
    //    "actif": true
    //  }
    @PostMapping("/{managerId}/restaurant/menus")
    public ResponseEntity<MenuDuJour> createMenu(
            @PathVariable Long managerId,
            @RequestBody MenuDuJour menu
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Associer le manager au menu
        menu.setManager(manager.get());
        MenuDuJour savedMenu = menuDuJourService.createMenu(menu);
        return ResponseEntity.ok(savedMenu);
    }

    // READ - Obtenir tous les menus
    // http://localhost:8080/managers/{managerId}/restaurant/menus
    @GetMapping("/{managerId}/restaurant/menus")
    public ResponseEntity<List<MenuDuJour>> getAllMenus(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(menuDuJourService.getAllMenus());
    }

    // READ - Obtenir un menu spécifique par ID
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

    // UPDATE - Mettre à jour un menu
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

    // DELETE - Supprimer un menu
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