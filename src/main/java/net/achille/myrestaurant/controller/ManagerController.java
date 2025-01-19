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

//    @Autowired
//    private MenuDuJourService menuDuJourService;

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

    @GetMapping("/{managerId}/restaurant")
    public ResponseEntity<RestaurantInfo> getManagerRestaurant(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RestaurantInfo restaurant = restaurantInfoService.getRestaurantByManagerId(managerId);
        return ResponseEntity.ok(restaurant);
    }

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

        entree.setManager(manager.get());
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


    @PutMapping("/{managerId}/restaurant/entrees/{entreeId}")
    public ResponseEntity<Entree> updateEntree(
            @PathVariable Long managerId,
            @PathVariable Long entreeId,
            @RequestBody Entree entree
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        entree.setId(entreeId);
        entree.setManager(manager.get());
        Entree updatedEntree = entreeService.saveEntree(entree);
        return ResponseEntity.ok(updatedEntree);
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

        plat.setManager(manager.get());
        Plat savedPlat = platService.savePlat(plat);
        return ResponseEntity.ok(savedPlat);
    }

    @GetMapping("/{managerId}/restaurant/plats")
    public ResponseEntity<List<Plat>> getPlats(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(platService.getPlatsByManagerId(managerId));
    }

    @PutMapping("/{managerId}/restaurant/plats/{platId}")
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

//
//    // ========== Gestion Menus ==========
//
//
//
//
//    @PostMapping("/{managerId}/restaurant/menus")
//    public ResponseEntity<MenuDuJour> createMenu(
//            @PathVariable Long managerId,
//            @RequestBody MenuDuJour menu
//    ) {
//        Optional<User> manager = managerService.getManagerById(managerId);
//        if (manager.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        menu.setManager(manager.get());
//        MenuDuJour savedMenu = menuDuJourService.createMenu(menu);
//        return ResponseEntity.ok(savedMenu);
//    }
//
//    @GetMapping("/{managerId}/restaurant/menus")
//    public ResponseEntity<List<MenuDuJour>> getAllMenus(@PathVariable Long managerId) {
//        Optional<User> manager = managerService.getManagerById(managerId);
//        if (manager.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(menuDuJourService.getMenusByManagerId(managerId));
//    }
//
//    @GetMapping("/{managerId}/restaurant/menus/{menuId}")
//    public ResponseEntity<MenuDuJour> getMenuById(
//            @PathVariable Long managerId,
//            @PathVariable Long menuId
//    ) {
//        Optional<User> manager = managerService.getManagerById(managerId);
//        if (manager.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        MenuDuJour menu = menuDuJourService.getMenuById(menuId);
//        return ResponseEntity.ok(menu);
//    }
//
//    @PutMapping("/{managerId}/restaurant/menus/{menuId}")
//    public ResponseEntity<MenuDuJour> updateMenu(
//            @PathVariable Long managerId,
//            @PathVariable Long menuId,
//            @RequestBody MenuDuJour menu
//    ) {
//        Optional<User> manager = managerService.getManagerById(managerId);
//        if (manager.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        menu.setId(menuId);
//        menu.setManager(manager.get());
//        MenuDuJour updatedMenu = menuDuJourService.createMenu(menu);
//        return ResponseEntity.ok(updatedMenu);
//    }
//
//    @DeleteMapping("/{managerId}/restaurant/menus/{menuId}")
//    public ResponseEntity<Void> deleteMenu(
//            @PathVariable Long managerId,
//            @PathVariable Long menuId
//    ) {
//        Optional<User> manager = managerService.getManagerById(managerId);
//        if (manager.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        menuDuJourService.deleteMenu(menuId);
//        return ResponseEntity.noContent().build();
//    }

//    // ========== Gestion NewMenus ==========

    @Autowired
    private MenuDuJourNewService menuDuJourNewService;

    // Ajoutez ces nouvelles méthodes à la fin de la classe ManagerController
    //http://localhost:8080/managers/1/restaurant/menus-new
    //
    //{
    //  "title": "Menus-new",
    //  "description": "Comprend une entrée, un plat, et un dessert.",
    //  "price": 25.99
    //}
    //

    @PostMapping("/{managerId}/restaurant/menus-new")
    public ResponseEntity<MenuDuJourNew> createMenuNew(
            @PathVariable Long managerId,
            @RequestBody MenuDuJourNew menu
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menu.setManager(manager.get());
        MenuDuJourNew savedMenu = menuDuJourNewService.saveMenu(menu);
        return ResponseEntity.ok(savedMenu);
    }



    @GetMapping("/{managerId}/restaurant/menus-new")
    public ResponseEntity<List<MenuDuJourNew>> getAllMenusNew(@PathVariable Long managerId) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(menuDuJourNewService.getMenusByManagerId(managerId));
    }

    @GetMapping("/{managerId}/restaurant/menus-new/{menuId}")
    public ResponseEntity<MenuDuJourNew> getMenuNewById(
            @PathVariable Long managerId,
            @PathVariable Long menuId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuDuJourNew> menu = menuDuJourNewService.getMenuById(menuId);
        return menu.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{managerId}/restaurant/menus-new/{menuId}")
    public ResponseEntity<MenuDuJourNew> updateMenuNew(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @RequestBody MenuDuJourNew menu
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menu.setId(menuId);
        menu.setManager(manager.get());
        MenuDuJourNew updatedMenu = menuDuJourNewService.saveMenu(menu);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{managerId}/restaurant/menus-new/{menuId}")
    public ResponseEntity<Void> deleteMenuNew(
            @PathVariable Long managerId,
            @PathVariable Long menuId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        menuDuJourNewService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }

    // Endpoints pour gérer les relations Crud plats
    //http://localhost:8080/managers/1/restaurant/menus-new/1/plats1

    @PostMapping("/{managerId}/restaurant/menus-new/{menuId}/plats/{platId}")
    public ResponseEntity<MenuDuJourNew> addPlatToMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long platId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MenuDuJourNew updatedMenu = menuDuJourNewService.addPlatToMenu(menuId, platId);
        return ResponseEntity.ok(updatedMenu);
    }

    @GetMapping("/{managerId}/restaurant/menus-new/{menuId}/plats")
    public ResponseEntity<List<Plat>> getPlatsByMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuDuJourNew> menu = menuDuJourNewService.getMenuById(menuId);
        if (menu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(menu.get().getPlats());
    }

    @GetMapping("/{managerId}/restaurant/menus-new/{menuId}/plats/{platId}")
    public ResponseEntity<Plat> getPlatFromMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long platId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuDuJourNew> menu = menuDuJourNewService.getMenuById(menuId);
        if (menu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Plat> plat = menu.get().getPlats().stream()
                .filter(p -> p.getId().equals(platId))
                .findFirst();

        return plat.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{managerId}/restaurant/menus-new/{menuId}/plats/{platId}")
    public ResponseEntity<MenuDuJourNew> removePlatFromMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long platId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MenuDuJourNew updatedMenu = menuDuJourNewService.removePlatFromMenu(menuId, platId);
        return ResponseEntity.ok(updatedMenu);
    }


    // ========== Gestion des relations Entrées-Menu ==========


    // add new-entre
    //http://localhost:8080/managers/1/restaurant/menus-new/1/entrees/2
    @PostMapping("/{managerId}/restaurant/menus-new/{menuId}/entrees/{entreeId}")
    public ResponseEntity<MenuDuJourNew> addEntreeToMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long entreeId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MenuDuJourNew updatedMenu = menuDuJourNewService.addEntreeToMenu(menuId, entreeId);
        return ResponseEntity.ok(updatedMenu);
    }

    @GetMapping("/{managerId}/restaurant/menus-new/{menuId}/entrees")
    public ResponseEntity<List<Entree>> getEntreesByMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuDuJourNew> menu = menuDuJourNewService.getMenuById(menuId);
        if (menu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(menu.get().getEntrees());
    }

    @GetMapping("/{managerId}/restaurant/menus-new/{menuId}/entrees/{entreeId}")
    public ResponseEntity<Entree> getEntreeFromMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long entreeId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuDuJourNew> menu = menuDuJourNewService.getMenuById(menuId);
        if (menu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Entree> entree = menu.get().getEntrees().stream()
                .filter(e -> e.getId().equals(entreeId))
                .findFirst();

        return entree.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{managerId}/restaurant/menus-new/{menuId}/entrees/{entreeId}")
    public ResponseEntity<MenuDuJourNew> removeEntreeFromMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long entreeId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MenuDuJourNew updatedMenu = menuDuJourNewService.removeEntreeFromMenu(menuId, entreeId);
        return ResponseEntity.ok(updatedMenu);
    }


    // ========== Gestion des relations Desserts-Menu ==========

    @PostMapping("/{managerId}/restaurant/menus-new/{menuId}/desserts/{dessertId}")
    public ResponseEntity<MenuDuJourNew> addDessertToMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long dessertId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MenuDuJourNew updatedMenu = menuDuJourNewService.addDessertToMenu(menuId, dessertId);
        return ResponseEntity.ok(updatedMenu);
    }

    @GetMapping("/{managerId}/restaurant/menus-new/{menuId}/desserts")
    public ResponseEntity<List<Dessert>> getDessertsByMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuDuJourNew> menu = menuDuJourNewService.getMenuById(menuId);
        if (menu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(menu.get().getDesserts());
    }

    @GetMapping("/{managerId}/restaurant/menus-new/{menuId}/desserts/{dessertId}")
    public ResponseEntity<Dessert> getDessertFromMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long dessertId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<MenuDuJourNew> menu = menuDuJourNewService.getMenuById(menuId);
        if (menu.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Dessert> dessert = menu.get().getDesserts().stream()
                .filter(d -> d.getId().equals(dessertId))
                .findFirst();

        return dessert.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{managerId}/restaurant/menus-new/{menuId}/desserts/{dessertId}")
    public ResponseEntity<MenuDuJourNew> removeDessertFromMenu(
            @PathVariable Long managerId,
            @PathVariable Long menuId,
            @PathVariable Long dessertId
    ) {
        Optional<User> manager = managerService.getManagerById(managerId);
        if (manager.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MenuDuJourNew updatedMenu = menuDuJourNewService.removeDessertFromMenu(menuId, dessertId);
        return ResponseEntity.ok(updatedMenu);
    }

}




