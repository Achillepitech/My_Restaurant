package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.Dessert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DessertRepository extends JpaRepository<Dessert, Long> {
    List<Dessert> findByManagerId(Long managerId);  // Ajouter cette méthode
}