package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlatRepository extends JpaRepository<Plat, Long> {
    // recherche des plats
    List<Plat> findByCategory(String category);
    List<Plat> findByMenuDuJour(boolean isMenuDuJour);
}
