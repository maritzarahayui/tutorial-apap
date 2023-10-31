package apap.tutorial.bacabaca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateRequestDTO {
    private String sourceLanguage;
    private String targetLanguage;
    private UUID bookId;
}
