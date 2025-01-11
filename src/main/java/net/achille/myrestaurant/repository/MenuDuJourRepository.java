package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.MenuDuJour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDuJourRepository extends JpaRepository<MenuDuJour, Long> {
}