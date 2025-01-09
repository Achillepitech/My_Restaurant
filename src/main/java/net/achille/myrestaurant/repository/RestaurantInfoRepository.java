package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, Long> {
    // Généralement, on n'aura qu'une seule entrée pour les infos du restaurant
}
