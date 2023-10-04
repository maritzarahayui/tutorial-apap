package apap.tutorial.bacabaca.dto.request;

import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.model.Penulis;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter
public class CreateBukuRequestDTO {
    @NotBlank(message = "Judul buku tidak boleh kosong")
    private String judul;

    @NotBlank(message = "Tahun terbit tidak boleh kosong")
    private String tahunTerbit;

    @Min(value = 0, message = "Harga buku harus lebih dari atau sama dengan 0")
    private BigDecimal harga;

    @NotNull(message = "Penerbit tidak boleh kosong")
    private Penerbit penerbit;

    private List<Penulis> listPenulis;
}
