package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.MenuDuJour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MenuDuJourRepository extends JpaRepository<MenuDuJour, Long> {
    Optional<MenuDuJour> findByDateAndActifTrue(LocalDate date);
    Optional<MenuDuJour> findTopByOrderByDateDesc();
}