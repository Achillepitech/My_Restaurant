package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.User;
import net.achille.myrestaurant.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    // Create a new manager
    public User createManager(User user) {
        return managerRepository.save(user);
    }

    // Get all managers
    public List<User> getAllManagers() {
        return managerRepository.findAll();
    }

    // Get a manager by ID
    public Optional<User> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    // Update a manager
    public Optional<User> updateManager(Long id, User userDetails) {
        return managerRepository.findById(id).map(manager -> {
            manager.setUsername(userDetails.getUsername());
            manager.setEmail(userDetails.getEmail());
            manager.setPassword(userDetails.getPassword());
            manager.setManagedRestaurant(userDetails.getManagedRestaurant());
            return managerRepository.save(manager);
        });
    }

    // Delete a manager
    public boolean deleteManager(Long id) {
        if (managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
