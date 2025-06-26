package fr.formation.Projet_Grp_Java.service;

import fr.formation.Projet_Grp_Java.feignclient.CommentaireFeignClient;
import fr.formation.Projet_Grp_Java.response.CommentaireResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final CommentaireFeignClient commentaireFeignClient;

    @CircuitBreaker(name = "produitServiceCB", fallbackMethod = "fallbackGetCommentaires")
    public List<CommentaireResponse> getCommentairesByProduitNom(String nom) {
        return commentaireFeignClient.findAllByProduitNom(nom);
    }

    public List<CommentaireResponse> fallbackGetCommentaires(String nom,
            Throwable t) {

        CommentaireResponse commentaire = new CommentaireResponse("Nom temporairement indisponible", -5);
        return List.of(commentaire);
    }

    @CircuitBreaker(name = "produitServiceCB", fallbackMethod = "fallbackGetNote")
    public int getNoteByProduitId(UUID produitId) {
        return commentaireFeignClient.getNoteByProduitId(produitId);
    }

    public int fallbackGetNote(UUID produitId, Throwable t) {

        return -5;
    }
}
