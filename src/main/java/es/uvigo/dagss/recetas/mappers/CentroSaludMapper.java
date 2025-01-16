package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.CentroSaludDto;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface CentroSaludMapper {
    CentroSaludDto toDto(CentroSalud centroSalud);
    CentroSalud toEntity(CentroSaludDto centroSaludDto);
    List<CentroSaludDto> toListDto(List<CentroSalud> centros);
}
