package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.MenuDuJourNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDuJourNewRepository extends JpaRepository<MenuDuJourNew, Long> {
    List<MenuDuJourNew> findByManagerId(Long managerId);
}
