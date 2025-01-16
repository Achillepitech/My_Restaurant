package net.achille.myrestaurant.controller;

import net.achille.myrestaurant.model.Plat;
import net.achille.myrestaurant.service.PlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")  // Nouveau chemin
@CrossOrigin("*")
public class PlatController {

    @Autowired
    private PlatService platService;

    @GetMapping("/{managerId}/restaurant/plats")
    public List<Plat> getPlatsByManager(@PathVariable Long managerId) {
        return platService.getPlatsByManagerId(managerId);
    }

    @PostMapping("/{managerId}/restaurant/plats")
    public Plat createPlat(@PathVariable Long managerId, @RequestBody Plat plat) {
        // Associer le manager
        plat.getManager().setId(managerId);
        return platService.savePlat(plat);
    }

    @PutMapping("/{managerId}/restaurant/plats/{platId}")
    public ResponseEntity<Plat> updatePlat(
            @PathVariable Long managerId,
            @PathVariable Long platId,
            @RequestBody Plat plat) {
        plat.setId(platId);
        plat.getManager().setId(managerId);
        Plat updatedPlat = platService.savePlat(plat);
        return ResponseEntity.ok(updatedPlat);
    }

    @DeleteMapping("/{managerId}/restaurant/plats/{platId}")
    public ResponseEntity<?> deletePlat(@PathVariable Long managerId, @PathVariable Long platId) {
        platService.deletePlat(platId);
        return ResponseEntity.ok().build();
    }

}