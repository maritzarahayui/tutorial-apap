package apap.tutorial.bacabaca.restservice;

import apap.tutorial.bacabaca.dto.request.TranslateRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdatePenerbitRequestDTO;
//import apap.tutorial.bacabaca.dto.response.BukuDetailResponse;
import apap.tutorial.bacabaca.dto.response.ResponseData;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.rest.BukuDetail;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BukuRestService {
    void createRestBuku(Buku buku);
    List<Buku> retrieveRestAllBuku();
    Buku getRestBukuById(UUID id);
    Mono<String> getStatus();
    Mono<BukuDetail> postStatus();
    // void restUpdateBuku(Buku buku);
    Mono<ResponseData> translateBookTitle(TranslateRequestDTO requestDTO);
    String getBookTitleById(UUID bookId);
    Map<String, Integer> getTopBooksInMonth(int month, int year);
    Buku restUpdateBuku(Buku bukuDTO);
    List<Buku> getAllBukuOrderedByJudul();
    List<Buku> searchBukuByJudul(String searchJudul);
}
