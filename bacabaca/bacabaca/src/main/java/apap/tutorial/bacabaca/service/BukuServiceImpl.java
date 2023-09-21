package apap.tutorial.bacabaca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import apap.tutorial.bacabaca.model.Buku;

@Service
public class BukuServiceImpl implements BukuService {
    private List<Buku> listBuku;

    public BukuServiceImpl() {
        listBuku = new ArrayList<>();
    }

    @Override
    public void createBuku(Buku buku) {
        listBuku.add(buku);
    }

    @Override
    public List<Buku> getAllBuku() {
        return listBuku;
    }

    @Override
    public Buku getBukuById(UUID id) {
        for (int i = 0; i < listBuku.size(); i++) {
            Buku buku = listBuku.get(i);
            if (buku.getId().equals(id)) {
                return buku;
            }
        }
        return null;
    }

    @Override
    public Buku updateBuku(UUID id, Buku updatedBuku) {
        for (Buku buku : listBuku) {
            if (buku.getId().equals(id)) {
                buku.setJudul(updatedBuku.getJudul());
                buku.setPenulis(updatedBuku.getPenulis());
                buku.setTahunTerbit(updatedBuku.getTahunTerbit());
                buku.setHarga(updatedBuku.getHarga());
                return buku;
            }
        }
        return null; // Tidak ada buku yang sesuai dengan ID
    }

    @Override
    public void deleteBuku(UUID id) {
        Buku bukuToRemove = null;
        for (Buku buku : listBuku) {
            if (buku.getId().equals(id)) {
                bukuToRemove = buku;
                break;
            }
        }

        if (bukuToRemove != null) {
            listBuku.remove(bukuToRemove);
        }
    }

    @Override
    public boolean isJudulExist(String judul) {
        for (Buku buku : listBuku) {
            if (buku.getJudul().equals(judul)) {
                return true; // Ada nih judul bukunya sama
            }
        }
        return false; // Gaada buku yang judulnya sama
    }

    @Override
    public boolean isJudulExist(String judul, UUID id) {
        for (Buku buku : listBuku) {
            if (buku.getJudul().equals(judul) && !buku.getId().equals(id)) {
                return true; // Ada buku yang judul & ID nya sama
            }
        }
        return false; // Gaada buku yang judul & ID nya sama
    }
}