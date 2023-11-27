package apap.tutorial.bacabaca.controller;

import apap.tutorial.bacabaca.dto.PenulisMapper;
import apap.tutorial.bacabaca.dto.request.CreatePenerbitRequestDTO;
import apap.tutorial.bacabaca.dto.request.CreatePenulisRequestDTO;
import apap.tutorial.bacabaca.dto.request.DeleteMultiplePenulisDTO;
import apap.tutorial.bacabaca.model.Sertifikasi;
import apap.tutorial.bacabaca.service.PenulisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PenulisController {
    @Autowired
    PenulisService penulisService;

    @Autowired
    PenulisMapper penulisMapper;

    @GetMapping("penulis/create")
    public String formAddPenulis(Model model){
        // Membuat DTO untuk dikirimkan ke view.
        var penulisDTO = new CreatePenulisRequestDTO();

        model.addAttribute("penulisDTO", penulisDTO);

        model.addAttribute("activePage", "Penulis");
        return "form-create-penulis";
    }

    @PostMapping("penulis/create")
    public String addPenulis(@ModelAttribute CreatePenulisRequestDTO createPenulisRequestDTO, Model model) {
        var penulis = penulisMapper.createPenulisRequestDTOToPenulis(createPenulisRequestDTO);

        if (penulis.getListSertifikasi() != null) {
            for (Sertifikasi s : penulis.getListSertifikasi()) {
                s.setPenulis(penulis);
            }
        }

        penulisService.createPenulis(penulis);

        model.addAttribute("penulis", createPenulisRequestDTO);

        model.addAttribute("activePage", "Penulis");
        return "success-create-penulis";
    }

    @GetMapping("penulis/viewall")
    public String listPenulis(Model model){
        var listPenulis = penulisService.getAllPenulis();
        var deleteDTO = new DeleteMultiplePenulisDTO();

        model.addAttribute("listPenulis", listPenulis);
        model.addAttribute("deleteDTO", deleteDTO);

        model.addAttribute("activePage", "Penulis");
        return "viewall-penulis";
    }

    @PostMapping("penulis/delete")
    public String deleteMultiplePenulis(@ModelAttribute DeleteMultiplePenulisDTO deleteDTO, Model model){
        penulisService.deleteListPenulis(deleteDTO.getListPenulis());

        model.addAttribute("activePage", "Penulis");
        return "success-delete-penulis";
    }

    @PostMapping(value = "penulis/create", params = {"addRow"})
    public String addRowSertifikasiPenulis(@ModelAttribute CreatePenulisRequestDTO createPenulisRequestDTO, Model model) {
        if (createPenulisRequestDTO.getListSertifikasi() == null || createPenulisRequestDTO.getListSertifikasi().size() == 0) {
            createPenulisRequestDTO.setListSertifikasi(new ArrayList<>());
        }

        createPenulisRequestDTO.getListSertifikasi().add(new Sertifikasi());

        model.addAttribute("penulisDTO", createPenulisRequestDTO);

        model.addAttribute("activePage", "Penulis");
        return "form-create-penulis";
    }

    @PostMapping(value = "penulis/create", params = {"deleteRow"})
    public String deleteRowSertifikasiPenulis(@ModelAttribute CreatePenulisRequestDTO createPenulisRequestDTO, @RequestParam("deleteRow") int row, Model model) {
        createPenulisRequestDTO.getListSertifikasi().remove(row);
        model.addAttribute("penulisDTO", createPenulisRequestDTO);

        model.addAttribute("activePage", "Penulis");
        return "form-create-penulis";
    }

}
