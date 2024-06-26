package apap.tutorial.bacabaca.restcontroller;

import apap.tutorial.bacabaca.dto.BukuMapper;
import apap.tutorial.bacabaca.dto.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.TranslateRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.response.ResponseData;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.rest.BukuDetail;
import apap.tutorial.bacabaca.restservice.BukuRestService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BukuRestController {
    @Autowired
    private BukuMapper bukuMapper;

    @Autowired
    private BukuRestService bukuRestService;

    @GetMapping(value = "/buku/view-all")
    private List<Buku> retrieveAllBuku(){
        return bukuRestService.retrieveRestAllBuku();
    }

    @GetMapping(value = "/buku/{id}")
    private Buku retrieveBuku(@PathVariable("id") String id){
        try{
            return bukuRestService.getRestBukuById(UUID.fromString(id));
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Buku " + id + " not found");
        }
    }

    @PostMapping(value = "/buku/create")
    public Buku restAddBuku(@Valid @RequestBody CreateBukuRequestDTO bukuDTO, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var buku = bukuMapper.createBukuRequestDTOToBuku(bukuDTO);
            bukuRestService.createRestBuku(buku);
            return buku;
        }
    }

    @GetMapping(value = "/buku/status")
    private Mono<String> getStatus(){
        return bukuRestService.getStatus();
    }

    @PostMapping(value = "/full")
    private Mono<BukuDetail> postStatus(){
        return bukuRestService.postStatus();
    }

    @PostMapping("/buku/translate")
    public Mono<ResponseData> translateBookTitle(@RequestBody TranslateRequestDTO requestDTO) {
        return bukuRestService.translateBookTitle(requestDTO);
    }

    @PutMapping(value = "/buku")
    public Buku restUpdateBuku(@Valid @RequestBody UpdateBukuRequestDTO bukuDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var buku = bukuMapper.updateBukuRequestDTOToBuku(bukuDTO);
            return bukuRestService.restUpdateBuku(buku);
        }
    }

    @GetMapping("/buku/search")
    public List<Buku> searchBukuByJudul(@RequestParam(name = "query", required = false) String query) {
        List<Buku> listBuku;

        if (StringUtils.isNotBlank(query)) {
            listBuku = bukuRestService.searchBukuByJudul(query);
        } else {
            listBuku = bukuRestService.getAllBukuOrderedByJudul();
        }

        return listBuku;
    }

    @GetMapping("/random")
    public ResponseEntity random(){
        Random random = new Random();
        var theBool = random.nextBoolean();
        if (theBool){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
