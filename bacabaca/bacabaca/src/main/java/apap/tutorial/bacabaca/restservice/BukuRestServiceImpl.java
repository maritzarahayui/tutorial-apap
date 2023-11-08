package apap.tutorial.bacabaca.restservice;

//import apap.tutorial.bacabaca.dto.response.BukuDetailResponse;
import apap.tutorial.bacabaca.dto.request.TranslateRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdatePenerbitRequestDTO;
import apap.tutorial.bacabaca.dto.response.ResponseData;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.repository.BukuDb;
import apap.tutorial.bacabaca.rest.BukuDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class BukuRestServiceImpl implements BukuRestService {
    @Autowired
    private BukuDb bukuDb;

    @Override
    public void createRestBuku(Buku buku){
        bukuDb.save(buku);
    }

    @Override
    public List<Buku> retrieveRestAllBuku(){
        return bukuDb.findAllByOrderByJudulAsc();
    }

    @Override
    public Buku getRestBukuById(UUID id){
        for(Buku buku : retrieveRestAllBuku()){
            if(buku.getId().equals(id)){
                return buku;
            }
        }
        return null;
    }

//    private final String mockUrl = System.getenv("MOCK_URL");
    private final String mockUrl = "https://5da76e64-c489-43a9-bb20-26193db3cfa3.mock.pstmn.io";

//    private final WebClient webClient;

//    public BukuRestServiceImpl(WebClient.Builder webClientBuilder){
//        this.webClient = webClientBuilder.baseUrl(mockUrl).build(); // mock server
//    }

    private final WebClient mockWebClient;
    private final WebClient translatorWebClient;

    @Autowired
    public BukuRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.mockWebClient = webClientBuilder.baseUrl("https://5da76e64-c489-43a9-bb20-26193db3cfa3.mock.pstmn.io").build();
        this.translatorWebClient = webClientBuilder.baseUrl("https://text-translator2.p.rapidapi.com").build();
    }

    @Override
    public Mono<String> getStatus(){
        return this.mockWebClient.get().uri("/rest/buku/status").retrieve().bodyToMono(String.class);
    }

    @Override
    public Mono<BukuDetail> postStatus(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("judul", "Buku Cara Membaca Jilid 2");
        data.add("tahunTerbit", "2003");
        var response = this.mockWebClient
                .post()
                .uri("/rest/buku/full-status")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(BukuDetail.class);

        return response;
    }

    // @Override
    // public void restUpdateBuku(Buku bukuDTO) {
    //     bukuDTO.setJudul(bukuDTO.getJudul());
    //     bukuDb.save(bukuDTO);
    // }

    @Override
    public Mono<ResponseData> translateBookTitle(TranslateRequestDTO requestDTO) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("source_language", requestDTO.getSourceLanguage());
        data.add("target_language", requestDTO.getTargetLanguage());
        data.add("text", getBookTitleById(requestDTO.getBookId()));

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", "text-translator2.p.rapidapi.com");
        headers.set("x-rapidapi-key", "341c356ad1msh48d4203da9d4bdap13930ajsn70a432a6ebcb");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Mono<String> translatedTitle = translatorWebClient.post()
                .uri("/translate")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(data)
                .retrieve()
                .bodyToMono(String.class);

        UUID bookId = requestDTO.getBookId();
        return translatedTitle.flatMap(translated -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(translated);
                String translatedText = jsonNode.path("data").path("translatedText").asText();

                Buku buku = bukuDb.findById(bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

                ResponseData bukuDetailResponseDTO = new ResponseData();
                bukuDetailResponseDTO.setId(buku.getId());
                bukuDetailResponseDTO.setJudul(buku.getJudul());
                bukuDetailResponseDTO.setJudulLower(translatedText);
                bukuDetailResponseDTO.setTahunTerbit(buku.getTahunTerbit());
                bukuDetailResponseDTO.setHarga(buku.getHarga());

                return Mono.just(bukuDetailResponseDTO);
            } catch (IOException e) {
                return Mono.error(new RuntimeException("Error parsing translation response", e));
            }
        });
    }

    @Override
    public String getBookTitleById(UUID bookId) {
        Buku buku = bukuDb.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        return buku.getJudul();
    }

    @Override
    public Map<String, Integer> getTopBooksInMonth(int month, int year) {
        try {
            String apiUrl = "https://hapi-books.p.rapidapi.com/month/" + year + "/" + month;

            WebClient.RequestHeadersSpec<?> requestSpec = mockWebClient
                    .get()
                    .uri(apiUrl)
                    .headers(headers -> {
                        headers.set("X-RapidAPI-Host", "hapi-books.p.rapidapi.com");
                        headers.set("X-RapidAPI-Key",
                                "341c356ad1msh48d4203da9d4bdap13930ajsn70a432a6ebcb");
                        headers.set("Content-Type", "application/x-www-form-urlencoded");
                    });

            String response = requestSpec.retrieve().bodyToMono(String.class).block();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            Map<String, Integer> getTopBooks = new HashMap<>();
            for (int i = 0; i < jsonNode.size(); i++) {
                String name = jsonNode.get(i).get("name").asText();
                int rating = jsonNode.get(i).get("rating").asInt();

                getTopBooks.put(name, rating);
            }

            return getTopBooks;
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengambil data buku populer: " + e.getMessage());
        }
    }

    @Override
    public Buku restUpdateBuku(Buku bukuDTO) {
        Buku buku = getRestBukuById(bukuDTO.getId());

        if (buku != null) {
            buku.setPenerbit(bukuDTO.getPenerbit());
            buku.setHarga(bukuDTO.getHarga());
            buku.setJudul(bukuDTO.getJudul());
            buku.setTahunTerbit(bukuDTO.getTahunTerbit());
            buku.setListPenulis(bukuDTO.getListPenulis());
            bukuDb.save(buku);
        }

        return buku;
    }

    @Override
    public List<Buku> getAllBukuOrderedByJudul() {
        return bukuDb.findAllByOrderByJudulAsc();
    }

    @Override
    public List<Buku> searchBukuByJudul(String searchJudul) {
        return bukuDb.findBukuByJudulContainingIgnoreCaseOrderByJudulAsc(searchJudul);
    }

}
