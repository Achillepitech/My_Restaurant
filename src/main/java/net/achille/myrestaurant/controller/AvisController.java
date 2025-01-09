//package net.achille.myrestaurant.controller;
//
//import net.achille.myrestaurant.model.Avis;
//import net.achille.myrestaurant.service.AvisService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
// //API REST pour la gestion des avis
// //Base URL http://localhost:8080/api/avis
//
//@RestController
//@RequestMapping("/api/avis")
//@CrossOrigin("*")
//public class AvisController {
//
//    private final AvisService avisService;
//
//    public AvisController(AvisService avisService) {
//        this.avisService = avisService;
//    }
//
//    //Récupère la liste des avis approuvés
//    // http://localhost:8080/api/avis
//    //       {
//    //          "id": 1,
//    //          "nom": "Jean Dupont",
//    //         "commentaire": "Excellent restaurant !",
//    //          "approuve": true
//    //        }
//
//
//    @GetMapping
//    public ResponseEntity<List<Avis>> getAvisApprouves() {
//        return ResponseEntity.ok(avisService.getAvisApprouves());
//    }
//
//
//     //Crée un nouvel avis
//     //POST http://localhost:8080/api/avis Nouvel avis
//
//     //  *  {
//     //     *   "nom": "Jean Dupont",
//     //     *   "commentaire": "Excellent restaurant !"
//     //     * },
//
//
//
//    @PostMapping
//    public ResponseEntity<Avis> createAvis(@RequestBody Avis avis) {
//        return ResponseEntity.ok(avisService.saveAvis(avis));
//    }
//
//
//     //Approuve un avis
//
//   //http://localhost:8080/api/avis/{id}/approuver
//
//    @PutMapping("/{id}/approuver")
//    public ResponseEntity<Void> approuverAvis(@PathVariable Long id) {
//        avisService.approuverAvis(id);
//        return ResponseEntity.ok().build();
//    }
//}