package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.FarmaciaDto;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface FarmaciaMapper {

    @Mapping(source = "direccion.localidad", target = "localidad")
    @Mapping(source = "direccion.provincia", target = "provincia")
    @Mapping(source = "activo", target = "activo") // Asumiendo que Farmacia tiene el campo 'activo'
    FarmaciaDto toDto(Farmacia farmacia);

//    FarmaciaDto toDto(Farmacia farmacia);
    Farmacia toEntity(FarmaciaDto farmaciaDto);
    List<FarmaciaDto> toListDto(List<Farmacia> farmacias);
}
