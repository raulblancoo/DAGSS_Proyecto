package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface RecetaMapper {

    @Mapping(source = "prescripcion.medicamento.nombreComercial", target = "medicamento")
    RecetaDto toDto(Receta receta);
    List<RecetaDto> toListDto(List<Receta> recetas);
}
