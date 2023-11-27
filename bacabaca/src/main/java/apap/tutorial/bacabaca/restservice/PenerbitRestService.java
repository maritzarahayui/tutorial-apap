package apap.tutorial.bacabaca.restservice;

import apap.tutorial.bacabaca.dto.request.UpdatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface PenerbitRestService {
    void createRestPenerbit(Penerbit penerbit);
    List<Penerbit> retrieveRestAllPenerbit();
    Penerbit getRestPenerbitById(Long id);
    Penerbit restUpdatePenerbit(Long idPenerbit, UpdatePenerbitRequestDTO penerbitDTO);
    void deleteRestPenerbit(Long idPenerbit);
}
