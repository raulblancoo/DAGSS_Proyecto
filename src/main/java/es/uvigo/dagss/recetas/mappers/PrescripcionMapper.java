package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.PrescripcionDto;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface PrescripcionMapper {
    @Mapping(source = "prescripcion.medicamento.nombreComercial", target = "medicamento")
    PrescripcionDto toDto(Prescripcion prescripcion);
    List<PrescripcionDto> toListDto(List<Prescripcion> prescripciones);

}
