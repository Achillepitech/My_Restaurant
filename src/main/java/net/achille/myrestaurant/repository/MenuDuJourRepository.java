package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.MenuDuJour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuDuJourRepository extends JpaRepository<MenuDuJour, Long> {
    List<MenuDuJour> findByManagerId(Long managerId);
}