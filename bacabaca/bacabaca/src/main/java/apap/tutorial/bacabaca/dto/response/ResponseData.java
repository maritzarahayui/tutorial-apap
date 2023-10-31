package apap.tutorial.bacabaca.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ResponseData {
    private UUID id;
    private String judul;
    private String judulLower;
    private String tahunTerbit;
    private BigDecimal harga;
}
