package apap.tutorial.bacabaca.dto;

import apap.tutorial.bacabaca.dto.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.response.ReadBukuResponseDTO;
import apap.tutorial.bacabaca.model.Buku;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BukuMapper {
    Buku createBukuRequestDTOToBuku(CreateBukuRequestDTO createBukuRequestDTO);

    Buku updateBukuRequestDTOToBuku(UpdateBukuRequestDTO updateBukuRequestDTO);

    UpdateBukuRequestDTO bukuToUpdateBukuRequestDTO(Buku buku);

    ReadBukuResponseDTO bukuToReadBukuResponseDTO(Buku buku);

    @AfterMapping
    default void setNamaPenerbit(@MappingTarget ReadBukuResponseDTO bukuResponseDTO, Buku buku) {
        if (buku.getPenerbit() != null) {
            bukuResponseDTO.setNamaPenerbit(buku.getPenerbit().getNamaPenerbit());
        }
    }
}
