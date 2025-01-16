package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, Long> {
    Optional<RestaurantInfo> findByManagerId(Long managerId);
}