package apap.tutorial.bacabaca.restservice;

import apap.tutorial.bacabaca.dto.request.UpdatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.repository.PenerbitDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PenerbitRestServiceImpl implements PenerbitRestService {
    @Autowired
    private PenerbitDb penerbitDb;

    @Override
    public List<Penerbit> retrieveRestAllPenerbit(){
        return penerbitDb.findAll();
    }

    @Override
    public Penerbit getRestPenerbitById(Long id){
        for(Penerbit penerbit : retrieveRestAllPenerbit()){
            if(penerbit.getIdPenerbit() == id){
                return penerbit;
            }
        }
        return null;
    }

    @Override
    public void createRestPenerbit(Penerbit penerbit){
        penerbitDb.save(penerbit);
    }

    @Override
    public Penerbit restUpdatePenerbit(Long idPenerbit, UpdatePenerbitRequestDTO penerbitDTO) {
        Penerbit penerbit = getRestPenerbitById(idPenerbit);

        if (penerbit != null) {
            penerbit.setNamaPenerbit(penerbitDTO.getNamaPenerbit());
            penerbit.setEmail(penerbitDTO.getEmail());
            penerbit.setAlamat(penerbitDTO.getAlamat());
            penerbitDb.save(penerbit);
        }

        return penerbit;
    }

    @Override
    public void deleteRestPenerbit(Long idPenerbit) {
        Penerbit penerbit = getRestPenerbitById(idPenerbit);
        if (!penerbit.getIsDeleted()){
            penerbit.setIsDeleted(true);
            penerbitDb.save(penerbit);
        }
    }
}
