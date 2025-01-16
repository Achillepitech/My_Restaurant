package net.achille.myrestaurant.repository;
import java.util.List;

import net.achille.myrestaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<User, Long> {
}
