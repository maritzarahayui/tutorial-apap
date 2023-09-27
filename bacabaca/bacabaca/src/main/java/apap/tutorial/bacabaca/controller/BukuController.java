package apap.tutorial.bacabaca.controller;

import apap.tutorial.bacabaca.dto.BukuMapper;
import apap.tutorial.bacabaca.dto.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.response.ReadBukuResponseDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.service.BukuService;
import apap.tutorial.bacabaca.service.PenerbitService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BukuController {
    @Autowired
    private BukuMapper bukuMapper;

    @Autowired
    private BukuService bukuService;

    @Autowired
    private PenerbitService penerbitService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("buku/create")
    public String formAddBuku(Model model) {
        // Membuat DTO baru sebagai isian form pengguna
        var bukuDTO = new CreateBukuRequestDTO();

        model.addAttribute("bukuDTO", bukuDTO);
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());

        return "form-create-buku";
    }

    @PostMapping("buku/create")
    public String addBuku(@Valid @ModelAttribute CreateBukuRequestDTO bukuDTO, BindingResult bindingResult, Model model) {
        // Generate id buku dengan UUID
//        UUID newId = UUID.randomUUID();

        if (bindingResult.hasErrors()){
            List<ObjectError> err = bindingResult.getAllErrors();

            StringBuilder errorMessage = new StringBuilder();

            for(ObjectError r : err){
                errorMessage.append(r.getDefaultMessage());
                errorMessage.append("\n");
            }

            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

        if (bukuService.isJudulExist(bukuDTO.getJudul())) {
            var errorMessage = "Maaf, judul buku sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }
        
        // Membuat object Buku dengan data yang berasal dari DTO
        var buku = bukuMapper.createBukuRequestDTOToBuku(bukuDTO);

        // Memanggil Service addBuku
        bukuService.saveBuku(buku);

        // Add variabel id buku ke 'id' untuk dirender di thymeleaf
        model.addAttribute("id", buku.getId());

        // Add variabel judul ke 'judul' untuk dirender di thymeleaf
        model.addAttribute("judul", buku.getJudul());

        return "success-create-buku";
    }

    @GetMapping("buku/viewall")
    public String listBuku(Model model) {
        // Mendapatkan semua buku
//        List<Buku> list = bukuService.getAllBuku();
        List<Buku> filtered = new ArrayList<>();

        for(Buku b : bukuService.getAllBukuOrderedByJudul()){
            if(!b.isDeleted()){
                filtered.add(b);
            }
        }

        // Add variabel semua bukuModel ke "ListBuku" untuk dirender pada thymeleaf
        model.addAttribute("listBuku", filtered);
        return "viewall-buku";
    }

    @GetMapping("buku/{id}")
    public String detailBuku(@PathVariable("id") UUID id, Model model) {
        // Mendapatkan buku melalui kodeBuku
//        var buku = bukuService.getBukuById(id);

        Buku buku = bukuService.getBukuById(id);
        ReadBukuResponseDTO bukuResponseDTO = bukuMapper.bukuToReadBukuResponseDTO(buku);

        model.addAttribute("buku", bukuResponseDTO);
        return "view-buku";
    }

    @GetMapping("buku/{id}/update")
    public String formUpdateBuku(@PathVariable("id") UUID id, Model model) {
        // Mengambil buku yang sesuai dengan id
        var buku = bukuService.getBukuById(id);

        // Memindahkan data buku ke DTO
        var bukuDTO = bukuMapper.bukuToUpdateBukuRequestDTO(buku);
        
        // Add variabel ke bukuDTO untuk dirender pada thymeleaf
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
        model.addAttribute("bukuDTO", bukuDTO);

        return "form-update-buku";
    }

    @PostMapping("buku/update")
    public String updateBuku(@Valid @ModelAttribute UpdateBukuRequestDTO bukuDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            List<ObjectError> err = bindingResult.getAllErrors();

            StringBuilder errorMessage = new StringBuilder();

            for(ObjectError r : err){
                errorMessage.append(r.getDefaultMessage());
                errorMessage.append("\n");
            }

            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

        if (bukuService.isJudulExist(bukuDTO.getJudul(), bukuDTO.getId())) {
            var errorMessage = "Maaf, judul buku sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

        var bukuFromDto = bukuMapper.updateBukuRequestDTOToBuku(bukuDTO);

        // Memanggil Serivce updateBuku
        var buku = bukuService.updateBuku(bukuFromDto);

        // Add variabel ke id untuk dirender pada thymeleaf
        model.addAttribute("id", buku.getId());

        // Add variabel ke judul untuk dirender pada thymeleaf
        model.addAttribute("judul", buku.getJudul());

        // Feedback bahwa data berhasil diubah
        return "success-update-buku";
    }

    @GetMapping("buku/{id}/delete")
    public String deleteBuku(@PathVariable("id") UUID id, Model model) {
        var buku =  bukuService.getBukuById(id);

        // Memanggil service deleteBuku untuk menghapus buku dengan ID yang sesuai
        bukuService.deleteBuku(buku);

        // Add variabel ke id untuk dirender pada thymeleaf
        model.addAttribute("id", id);

        // Feedback bahwa data berhasil dihapus
        return "success-delete-buku";
    }

    @GetMapping("/buku/search")
    public String searchBukuByJudul(@RequestParam(name = "searchJudul", required = false) String searchJudul, Model model) {
        List<Buku> listBuku;

        // Kalo search field ga kosong, bakal nyari bukunya
        if (StringUtils.isNotBlank(searchJudul)) {
            listBuku = bukuService.searchBukuByJudul(searchJudul);
        } else {
            // Kalo search field kosong, by default bakal nampilin semua buku
            listBuku = bukuService.getAllBukuOrderedByJudul();
        }

        model.addAttribute("listBuku", listBuku);
        return "viewall-buku";
    }

}
