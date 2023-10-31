package apap.tutorial.bacabaca.controller;

import apap.tutorial.bacabaca.dto.PenerbitMapper;
import apap.tutorial.bacabaca.dto.request.CreatePenerbitRequestDTO;
import apap.tutorial.bacabaca.service.BukuService;
import apap.tutorial.bacabaca.service.PenerbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class PenerbitController {
    @Autowired
    PenerbitService penerbitService;

    @Autowired
    BukuService bukuService;

    @Autowired
    PenerbitMapper penerbitMapper;

    @GetMapping("penerbit/create")
    public String formAddPenerbit(Model model){
        // Membuat penerbitDTO baru untuk diisi di form
        var penerbitDTO = new CreatePenerbitRequestDTO();

        // Menambah penerbitDTO ke model thymyleaf
        model.addAttribute("penerbitDTO", penerbitDTO);

        model.addAttribute("activePage", "Penerbit");
        return "form-create-penerbit";
    }

    @PostMapping("penerbit/create")
    public String addPenerbit(@ModelAttribute CreatePenerbitRequestDTO createPenerbitRequestDTO, Model model){
        // Membuat object penerbit dengan data yang berasal dari DTO
        var penerbit = penerbitMapper.createPenerbitRequestDTOToPenerbit(createPenerbitRequestDTO);

        // Memanggil service createPenerbit
        penerbit = penerbitService.createPenerbit(penerbit);

        // Menambah penerbit ke model thymyleaf
        model.addAttribute("penerbit", createPenerbitRequestDTO);

        model.addAttribute("activePage", "Penerbit");
        return "success-create-penerbit";
    }

    @GetMapping("penerbit/viewall")
    public String listPenerbit(Model model){
        // Membuat penerbitDTO baru untuk diisi di form
        var listPenerbit = penerbitService.getAllPenerbit();

        // Menambah penerbitDTO ke model thymyleaf
        model.addAttribute("listPenerbit", listPenerbit);

        model.addAttribute("activePage", "Penerbit");
        return "viewall-penerbit";
    }

    @GetMapping("penerbit/{idPenerbit}")
    public String detailPenerbit(@PathVariable("idPenerbit") Long idPenerbit, Model model){
        // Mendapatkan buku melalui kode buku
        var penerbit = penerbitService.getPenerbitById(idPenerbit);

        model.addAttribute("penerbit", penerbit);

        model.addAttribute("activePage", "Penerbit");
        return "view-penerbit";
    }

    @GetMapping("penerbit/chart")
    public String chartPenerbit(Model model){
        var listPenerbit = penerbitService.getPublisherBookCounts();
        model.addAttribute("listPenerbit", listPenerbit);

        return "view-penerbit-chart";
    }


}
