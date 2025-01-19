package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel="spring")
public interface RecetaMapper {

    @Mapping(source = "prescripcion.medicamento.nombreComercial", target = "medicamento")
    @Mapping(source = "prescripcion.medico", target = "medico", qualifiedByName = "concatNombreApellidosMedico")
    @Mapping(target = "puedeSerServida", ignore = true) // Ignorar durante el mapeo inicial
    RecetaDto toDto(Receta receta);

    List<RecetaDto> toListDto(List<Receta> recetas);

    @Named("concatNombreApellidosMedico")
    default String concatNombreApellidosMedico(Medico medico) {
        return medico.getNombre() + " " + medico.getApellidos();
    }
}
