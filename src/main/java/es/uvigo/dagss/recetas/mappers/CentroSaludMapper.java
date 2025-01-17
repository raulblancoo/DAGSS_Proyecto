package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.CentroSaludDto;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface CentroSaludMapper {

    @Mapping(source = "direccion.localidad", target = "localidad")
    @Mapping(source = "direccion.provincia", target = "provincia")
    CentroSaludDto toDto(CentroSalud centroSalud);
    CentroSalud toEntity(CentroSaludDto centroSaludDto);
    List<CentroSaludDto> toListDto(List<CentroSalud> centros);
}
