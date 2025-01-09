//package net.achille.myrestaurant.repository;
//
//import net.achille.myrestaurant.model.Avis;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface AvisRepository extends JpaRepository<Avis, Long> {
//    // Trouver tous les avis approuvés
//    List<Avis> findByApprouveTrue();
//
//    // Trouver les avis par note
//    List<Avis> findByNoteGreaterThanEqual(Integer note);
//}