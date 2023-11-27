package apap.tutorial.bacabaca.dto.request;

import apap.tutorial.bacabaca.model.Sertifikasi;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePenulisRequestDTO {
    @NotBlank
    private String namaPenulis;

    @NotBlank
    private String biografi;

    @NotEmpty
    private List<Sertifikasi> listSertifikasi;
}
