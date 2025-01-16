package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.Plat;
import net.achille.myrestaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatRepository extends JpaRepository<Plat, Long> {
    List<Plat> findByManager_Id(Long managerId);  // Notez le underscore entre Manager et Id
}
