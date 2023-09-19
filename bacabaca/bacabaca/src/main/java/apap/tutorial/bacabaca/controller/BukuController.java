package apap.tutorial.bacabaca.controller;

import apap.tutorial.bacabaca.controller.DTO.BukuDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.service.BukuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class BukuController {

    @Autowired
    private BukuService bukuService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("buku/create")
    public String formAddBuku(Model model) {
        // Membuat DTO baru sebagai isian form pengguna
        var bukuDTO = new BukuDTO();

        model.addAttribute("bukuDTO", bukuDTO);

        return "form-create-buku";
    }

    @PostMapping("buku/create")
    public String addBuku(@ModelAttribute BukuDTO bukuDTO, Model model) {
        // Generate id buku dengan UUID
        UUID newId = UUID.randomUUID();

        if (bukuService.isJudulExist(bukuDTO.getJudul())) {
            var errorMessage = "Maaf, judul buku sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }
        
        // Membuat object Buku dengan data yang berasal dari DTO
        var buku = new Buku(newId, bukuDTO.getJudul(), 
            bukuDTO.getPenulis(), bukuDTO.getTahunTerbit(), bukuDTO.getHarga());

        // Memanggil Service addBuku
        bukuService.createBuku(buku);

        // Add variabel id buku ke 'id' untuk dirender di thymeleaf
        model.addAttribute("id", buku.getId());

        // Add variabel judul ke 'judul' untuk dirender di thymeleaf
        model.addAttribute("judul", buku.getJudul());

        return "success-create-buku";
    }

    @GetMapping("buku/viewall")
    public String listBuku(Model model) {
        // Mendapatkan semua buku
        List<Buku> listBuku = bukuService.getAllBuku();

        // Add variabel semua bukuModel ke "ListBuku" untuk dirender pada thymeleaf
        model.addAttribute("listBuku", listBuku);
        return "viewall-buku";
    }

    @GetMapping("buku/{id}")
    public String detailBuku(@PathVariable("id") UUID id, Model model) {
        // Mendapatkan buku melalui kodeBuku
        var buku = bukuService.getBukuById(id);

        model.addAttribute("buku", buku);
        return "view-buku";
    }

    @GetMapping("buku/{id}/update")
    public String formUpdateBuku(@PathVariable("id") UUID id, Model model) {
        // Mengambil buku yang sesuai dengan id
        var buku = bukuService.getBukuById(id);

        // Memindahkan data buku ke DTO
        var bukuDTO = new BukuDTO(buku.getId(), 
            buku.getJudul(), buku.getPenulis(), buku.getTahunTerbit(), buku.getHarga());
        
        // Add variabel ke bukuDTO untuk dirender pada thymeleaf
        model.addAttribute("bukuDTO", bukuDTO);

        return "form-update-buku";
    }

    @PostMapping("buku/update")
    public String updateBuku(@ModelAttribute BukuDTO updatedBukuDTO, Model model) {
        // Validasi jika buku sudah pernah didaftarkan
        if (bukuService.isJudulExist(updatedBukuDTO.getJudul(), updatedBukuDTO.getId())) {
            var errorMessage = "Maaf, judul buku sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

        var buku = new Buku(updatedBukuDTO.getId(), updatedBukuDTO.getJudul(), 
            updatedBukuDTO.getPenulis(), updatedBukuDTO.getTahunTerbit(), updatedBukuDTO.getHarga());

        // Update variabel buku dengan memanggil service updateBuku
        bukuService.updateBuku(buku.getId(),buku);

        // Add variabel ke id untuk dirender pada thymeleaf
        model.addAttribute("id", buku.getId());

        // Add variabel ke judul untuk dirender pada thymeleaf
        model.addAttribute("judul", buku.getJudul());

        // Feedback bahwa data berhasil diubah
        return "success-update-buku";
    }

    @GetMapping("buku/{id}/delete")
    public String deleteBuku(@PathVariable("id") UUID id, Model model) {
        // Memanggil service deleteBuku untuk menghapus buku dengan ID yang sesuai
        bukuService.deleteBuku(id);

        // Add variabel ke id untuk dirender pada thymeleaf
        model.addAttribute("id", id);

        // Feedback bahwa data berhasil dihapus
        return "success-delete-buku";
    }
}