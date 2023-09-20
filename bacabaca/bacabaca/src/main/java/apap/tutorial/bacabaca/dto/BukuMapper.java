package apap.tutorial.bacabaca.dto;

import apap.tutorial.bacabaca.dto.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.dto.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.model.Buku;
import org.mapstruct.Mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BukuMapper {
    Buku createBukuRequestDTOToBuku(CreateBukuRequestDTO createBukuRequestDTO);

    Buku updateBukuRequestDTOToBuku(UpdateBukuRequestDTO updateBukuRequestDTO);

    UpdateBukuRequestDTO bukuToUpdateBukuRequestDTO(Buku buku);
}
