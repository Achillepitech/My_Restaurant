package net.achille.myrestaurant.service;

import net.achille.myrestaurant.model.RestaurantInfo;
import net.achille.myrestaurant.repository.RestaurantInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RestaurantInfoService {

    @Autowired
    private RestaurantInfoRepository restaurantInfoRepository;

    // Récupérer les informations du restaurant
    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfoRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    RestaurantInfo defaultInfo = new RestaurantInfo();
                    defaultInfo.setNom("Restaurant par défaut");
                    defaultInfo.setDescription("Description par défaut");
                    return defaultInfo;
                });
    }

    // Mettre à jour ou créer les informations du restaurant
    public RestaurantInfo saveOrUpdateRestaurantInfo(RestaurantInfo info) {
        RestaurantInfo existingInfo = getRestaurantInfo();
        if (existingInfo.getId() != null) {
            info.setId(existingInfo.getId());
        }
        return restaurantInfoRepository.save(info);
    }

    public Optional<RestaurantInfo> getRestaurantById(Long restaurantId) {
        return restaurantInfoRepository.findById(restaurantId);
    }

    public void deleteRestaurant(Long restaurantId) {
        restaurantInfoRepository.deleteById(restaurantId);
    }
}
