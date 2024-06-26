package apap.tutorial.bacabaca.service;

import java.util.List;
import java.util.UUID;

import apap.tutorial.bacabaca.dto.BukuMapper;
import apap.tutorial.bacabaca.repository.BukuDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tutorial.bacabaca.model.Buku;

@Service
public class BukuServiceImpl implements BukuService {
    @Autowired
    BukuDb bukuDb;

    @Autowired
    BukuMapper bukuMapper;

    @Override
    public void saveBuku(Buku buku){
        bukuDb.save(buku);
    }

    @Override
    public List<Buku> getAllBuku(){
        return bukuDb.findAll();
    }

    @Override
    public Buku getBukuById(UUID kodeBuku){
        for (Buku buku : getAllBuku()){
            if(buku.getId().equals(kodeBuku)){
                return buku;
            }
        }
        return null;
    }

    @Override
    public Buku updateBuku(Buku bukuFromDto){
        Buku buku = getBukuById(bukuFromDto.getId());
        if (buku != null){
            buku.setHarga(bukuFromDto.getHarga());
            buku.setJudul(bukuFromDto.getJudul());
            buku.setListPenulis(bukuFromDto.getListPenulis());
            buku.setTahunTerbit(bukuFromDto.getTahunTerbit());
            buku.setPenerbit(bukuFromDto.getPenerbit());
            bukuDb.save(buku);
        }
        return buku;
    }

    @Override
    public boolean isJudulExist(String judul){
        return getAllBuku().stream().anyMatch(b -> b.getJudul().equals(judul));
    }

    @Override
    public boolean isJudulExist(String judul, UUID id){
        return getAllBuku().stream().anyMatch(b -> b.getJudul().equals(judul) && !b.getId().equals(id));
    }

    @Override
    public void deleteBuku(Buku buku){
        buku.setDeleted(true);
        bukuDb.save(buku);
    }

    // Method untuk mencari buku berdasarkan judul
    @Override
    public List<Buku> searchBukuByJudul(String searchJudul) {
        return bukuDb.findBukuByJudulContainingIgnoreCaseOrderByJudulAsc(searchJudul);
    }

    // Method untuk menampilkan buku berdasarkan judul secara Ascending (A -> Z)
    @Override
    public List<Buku> getAllBukuOrderedByJudul() {
        return bukuDb.findAllByOrderByJudulAsc();
    }

}
