package apap.tutorial.bacabaca.restcontroller;

import apap.tutorial.bacabaca.dto.BukuMapper;
import apap.tutorial.bacabaca.dto.PenerbitMapper;
import apap.tutorial.bacabaca.dto.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.CreatePenerbitRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.restservice.PenerbitRestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PenerbitRestController {
    @Autowired
    private PenerbitMapper penerbitMapper;

    @Autowired
    private PenerbitRestService penerbitRestService;

    @GetMapping(value = "/penerbit/view-all")
    private List<Penerbit> retrieveAllPenerbit(){
        return penerbitRestService.retrieveRestAllPenerbit();
    }

    @GetMapping(value = "/penerbit/{idPenerbit}")
    private Penerbit retrievePenerbit(@PathVariable Long idPenerbit){
        try{
            return penerbitRestService.getRestPenerbitById(idPenerbit);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Penerbit " + idPenerbit + " not found");
        }
    }

    @PostMapping(value = "/penerbit/create")
    private Penerbit restAddPenerbit(@Valid @RequestBody CreatePenerbitRequestDTO penerbitDTO, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var penerbit = penerbitMapper.createPenerbitRequestDTOToPenerbit(penerbitDTO);
            penerbitRestService.createRestPenerbit(penerbit);
            return penerbit;
        }
    }

    @PutMapping(value = "/penerbit/{idPenerbit}")
    private Penerbit restUpdatePenerbit(@PathVariable("idPenerbit") String idPenerbit, @RequestBody UpdatePenerbitRequestDTO penerbitDTO) {
        try {
            Penerbit penerbit = penerbitRestService.restUpdatePenerbit(Long.parseLong(idPenerbit), penerbitDTO);
            return penerbit;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Id penerbit " + idPenerbit + "not found"
            );
        }
    }

    @DeleteMapping(value = "/penerbit/{idPenerbit}")
    private ResponseEntity<String> restDeletePenerbit(@PathVariable Long idPenerbit) {
        try {
            penerbitRestService.deleteRestPenerbit(idPenerbit);
            return ResponseEntity.ok("Penerbit has been deleted");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Id penerbit " + idPenerbit + "not found"
            );
        }
    }

}
