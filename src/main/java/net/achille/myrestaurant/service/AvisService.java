//package net.achille.myrestaurant.service;
//
//import net.achille.myrestaurant.model.Avis;
//import net.achille.myrestaurant.repository.AvisRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//
//@Service
//@Transactional
//public class AvisService {
//
//    private final AvisRepository avisRepository;
//
//    public AvisService(AvisRepository avisRepository) {
//        this.avisRepository = avisRepository;
//    }
//
//    public List<Avis> getAvisApprouves() {
//        return avisRepository.findByApprouveTrue();
//    }
//
//    public Avis saveAvis(Avis avis) {
//        return avisRepository.save(avis);
//    }
//
//    public void approuverAvis(Long id) {
//        Avis avis = avisRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Avis non trouvé"));
//        avis.setApprouve(true);
//        avisRepository.save(avis);
//    }
//}