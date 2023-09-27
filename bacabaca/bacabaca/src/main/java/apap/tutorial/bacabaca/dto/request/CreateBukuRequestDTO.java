package apap.tutorial.bacabaca.dto.request;

import apap.tutorial.bacabaca.model.Penerbit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBukuRequestDTO {
    @NotBlank(message = "Judul buku tidak boleh kosong")
    private String judul;

    @NotBlank(message = "Tahun terbit tidak boleh kosong")
    private String tahunTerbit;

    @Min(value = 0, message = "Harga buku harus lebih dari atau sama dengan 0")
    private BigDecimal harga;

    @NotNull(message = "Penerbit tidak boleh kosong")
    private Penerbit penerbit;
}
