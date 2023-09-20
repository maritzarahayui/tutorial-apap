package apap.tutorial.bacabaca.dto.request;

import apap.tutorial.bacabaca.model.Penerbit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBukuRequestDTO {
    private String judul;
    private String tahunTerbit;
    private BigDecimal harga;
    private Penerbit penerbit;
}
