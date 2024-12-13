package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface RecetaMapper {
    RecetaDto toDto(Receta receta);
    Receta toEntity(RecetaDto userDto);
    List<RecetaDto> toListDto(List<Receta> users);
}
