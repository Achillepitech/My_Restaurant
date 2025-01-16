package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.RestaurantInfo;
import net.achille.myrestaurant.repository.RestaurantInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestaurantInfoService {
    @Autowired
    private RestaurantInfoRepository restaurantInfoRepository;

    public List<RestaurantInfo> getAllRestaurants() {
        return restaurantInfoRepository.findAll();
    }

    public RestaurantInfo getRestaurantById(Long id) {
        return restaurantInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    public RestaurantInfo saveOrUpdateRestaurantInfo(RestaurantInfo info) {
        return restaurantInfoRepository.save(info);
    }

    public void deleteRestaurant(Long id) {
        restaurantInfoRepository.deleteById(id);
    }

    public RestaurantInfo getRestaurantByManagerId(Long managerId) {
        return restaurantInfoRepository.findByManagerId(managerId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found for manager id: " + managerId));
    }
}