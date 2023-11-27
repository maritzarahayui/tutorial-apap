package apap.tutorial.bacabaca.controller;

import apap.tutorial.bacabaca.dto.BukuMapper;
import apap.tutorial.bacabaca.dto.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.CreatePenulisRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.response.ReadBukuResponseDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penulis;
import apap.tutorial.bacabaca.restservice.BukuRestService;
import apap.tutorial.bacabaca.service.BukuService;
import apap.tutorial.bacabaca.service.PenerbitService;
import apap.tutorial.bacabaca.service.PenulisService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;

@Controller
public class BukuController {
    @Autowired
    private BukuMapper bukuMapper;

    @Autowired
    private BukuService bukuService;

    @Autowired
    private PenerbitService penerbitService;

    @Autowired
    private PenulisService penulisService;

    @Autowired
    private BukuRestService bukuRestService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage", "Beranda");
        return "home";
    }

    @GetMapping("buku/create")
    public String formAddBuku(Model model) {
        // Membuat DTO baru sebagai isian form pengguna
        var bukuDTO = new CreateBukuRequestDTO();
        model.addAttribute("bukuDTO", bukuDTO);

        var listPenerbit = penerbitService.getAllPenerbit();
        model.addAttribute("listPenerbit", listPenerbit);

        var listPenulisExisting = penulisService.getAllPenulis();
        model.addAttribute("listPenulisExisting", listPenulisExisting);

        model.addAttribute("activePage", "Buku");
        return "form-create-buku";
    }

    @PostMapping(value = "buku/create", params = {"addRow"})
    public String addRowPenulisBuku(@ModelAttribute CreateBukuRequestDTO createBukuRequestDTO, Model model){
        if(createBukuRequestDTO.getListPenulis() == null || createBukuRequestDTO.getListPenulis().size() == 0){
            createBukuRequestDTO.setListPenulis(new ArrayList<>());
        }

        createBukuRequestDTO.getListPenulis().add(new Penulis());

        model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
        model.addAttribute("bukuDTO", createBukuRequestDTO);

        model.addAttribute("activePage", "Buku");
        return "form-create-buku";
    }

    @PostMapping(value = "buku/create", params = {"deleteRow"})
    public String deleteRowPenulisBuku(@ModelAttribute CreateBukuRequestDTO createBukuRequestDTO, @RequestParam("deleteRow") int row, Model model){
        createBukuRequestDTO.getListPenulis().remove(row);

        model.addAttribute("bukuDTO", createBukuRequestDTO);
        model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());

        model.addAttribute("activePage", "Buku");
        return "form-create-buku";
    }

    @PostMapping("buku/create")
    public String addBuku(@Valid @ModelAttribute CreateBukuRequestDTO bukuDTO, BindingResult bindingResult, Model model) {

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

        model.addAttribute("activePage", "Buku");
        return "success-create-buku";
    }

    @GetMapping("buku/viewall")
    public String listBuku(Model model) {
        List<Buku> filtered = new ArrayList<>();

        for(Buku b : bukuService.getAllBukuOrderedByJudul()){
            if(!b.isDeleted()){
                filtered.add(b);
            }
        }

        // Add variabel semua bukuModel ke "ListBuku" untuk dirender pada thymeleaf
        model.addAttribute("listBuku", filtered);

        model.addAttribute("activePage", "Buku");
        return "viewall-buku";
    }

    @GetMapping("buku/{id}")
    public String detailBuku(@PathVariable("id") UUID id, Model model) {

        Buku buku = bukuService.getBukuById(id);
        ReadBukuResponseDTO bukuResponseDTO = bukuMapper.bukuToReadBukuResponseDTO(buku);

        model.addAttribute("buku", bukuResponseDTO);

        model.addAttribute("activePage", "Buku");
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

        // Load all existing authors
        var listPenulisExisting = penulisService.getAllPenulis();
        model.addAttribute("listPenulisExisting", listPenulisExisting);

        model.addAttribute("activePage", "Buku");
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

        // Update penulis buku
        bukuFromDto.setListPenulis(bukuDTO.getListPenulis());

        // Memanggil Serivce updateBuku
        var buku = bukuService.updateBuku(bukuFromDto);

        // Add variabel ke id untuk dirender pada thymeleaf
        model.addAttribute("id", buku.getId());

        // Add variabel ke judul untuk dirender pada thymeleaf
        model.addAttribute("judul", buku.getJudul());

        model.addAttribute("activePage", "Buku");
        return "success-update-buku";
    }

    @PostMapping(value = "buku/update", params = {"addRow"})
    public String addRowPenulisBuku(@ModelAttribute UpdateBukuRequestDTO updateBukuRequestDTO, Model model){
        if(updateBukuRequestDTO.getListPenulis() == null || updateBukuRequestDTO.getListPenulis().size() == 0){
            updateBukuRequestDTO.setListPenulis(new ArrayList<>());
        }

        updateBukuRequestDTO.getListPenulis().add(new Penulis());

        model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
        model.addAttribute("bukuDTO", updateBukuRequestDTO);

        model.addAttribute("activePage", "Buku");
        return "form-update-buku";
    }

    @PostMapping(value = "buku/update", params = {"deleteRow"})
    public String deleteRowPenulisBuku(@ModelAttribute UpdateBukuRequestDTO updateBukuRequestDTO, @RequestParam("deleteRow") int row, Model model){
        updateBukuRequestDTO.getListPenulis().remove(row);

        model.addAttribute("bukuDTO", updateBukuRequestDTO);
        model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());

        model.addAttribute("activePage", "Buku");
        return "form-update-buku";
    }

    @GetMapping("buku/{id}/delete")
    public String deleteBuku(@PathVariable("id") UUID id, Model model) {
        var buku =  bukuService.getBukuById(id);

        // Memanggil service deleteBuku untuk menghapus buku dengan ID yang sesuai
        bukuService.deleteBuku(buku);

        // Add variabel ke id untuk dirender pada thymeleaf
        model.addAttribute("id", id);

        model.addAttribute("activePage", "Buku");
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

        model.addAttribute("activePage", "Buku");
        return "viewall-buku";
    }

    @GetMapping("/buku/viewall-with-datatables")
    public String viewAllBukuWithDatatables(Model model) {
        List<Buku> listBuku = bukuService.getAllBukuOrderedByJudul();

        model.addAttribute("listBuku", listBuku);

        model.addAttribute("activePage", "Buku");
        return "viewall-with-datatables";
    }

    @GetMapping("/buku/chart")
    public String getTopBooksInMonth(Model model) {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        var listBukuPopuler = bukuRestService.getTopBooksInMonth(month, year);

        model.addAttribute("listBukuPopuler", listBukuPopuler);

        model.addAttribute("activePage", "Buku");
        return "chart";
    }

}
