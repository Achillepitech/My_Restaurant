package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.RestaurantInfo;
import net.achille.myrestaurant.service.RestaurantInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin("*")
public class RestaurantController {

    private final RestaurantInfoService restaurantInfoService;

    public RestaurantController(RestaurantInfoService restaurantInfoService) {
        this.restaurantInfoService = restaurantInfoService;
    }

    @GetMapping("/info")
    public ResponseEntity<RestaurantInfo> getRestaurantInfo() {
        return ResponseEntity.ok(restaurantInfoService.getRestaurantInfo());
    }

    @PutMapping("/info")
    public ResponseEntity<RestaurantInfo> updateRestaurantInfo(@RequestBody RestaurantInfo restaurantInfo) {
        return ResponseEntity.ok(restaurantInfoService.saveOrUpdateRestaurantInfo(restaurantInfo));
    }


}