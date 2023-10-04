package apap.tutorial.bacabaca.service;

import apap.tutorial.bacabaca.model.Penulis;
import apap.tutorial.bacabaca.repository.PenulisDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PenulisServiceImpl implements PenulisService {
    @Autowired
    PenulisDb penulisDb;

    @Override
    public void createPenulis(Penulis penulis){
        penulisDb.save(penulis);
    }

    @Override
    public List<Penulis> getAllPenulis(){
        return penulisDb.findAll();
    }

    @Override
    public void deleteListPenulis(List<Penulis> listPenulis){
        var idPenulis = new ArrayList<Long>();

        if (listPenulis != null){
            for (Penulis penulis : listPenulis){
                if (penulis.getListBuku() == null || penulis.getListBuku().isEmpty()){
                    idPenulis.add(penulis.getIdPenulis());
                }
            }
        }

        penulisDb.deleteByIdPenulisIn(idPenulis);
    }
}
