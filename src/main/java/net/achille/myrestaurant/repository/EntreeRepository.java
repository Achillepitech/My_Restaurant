package net.achille.myrestaurant.repository;

import net.achille.myrestaurant.model.Entree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntreeRepository extends JpaRepository<Entree, Long> {
    List<Entree> findByManagerId(Long managerId);
}