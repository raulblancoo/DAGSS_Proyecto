package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.CitaDto;
import es.uvigo.dagss.recetas.entidades.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface CitaMapper {
    @Mapping(source = "paciente.nombre", target = "pacienteNombre")
    @Mapping(source = "medico.nombre", target = "medicoNombre")
    @Mapping(source = "centroSalud.nombre", target = "centroSaludNombre")
    CitaDto toDto(Cita cita);

    List<CitaDto> toListDto(List<Cita> citas);

    // Si necesitas mapear de DTO a entidad en el futuro, puedes agregar métodos aquí
    // Por ejemplo:
    // @Mapping(source = "pacienteNombre", target = "paciente.nombre")
    // Cita toEntity(CitaDto citaDto);
}
