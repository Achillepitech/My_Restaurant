package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, Long> {

}

// à ajouter les autres restaurants step 2 week 2
