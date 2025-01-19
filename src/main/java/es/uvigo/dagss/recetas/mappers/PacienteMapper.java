package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.PacienteCreateDto;
import es.uvigo.dagss.recetas.dtos.PacienteDto;
import es.uvigo.dagss.recetas.dtos.PacienteProfile;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(pacienteCreateDto.getDni())")
    @Mapping(source = "centroSaludId", target = "centroSalud.id")
    @Mapping(source = "medicoId", target = "medico.id")
    Paciente toEntity(PacienteCreateDto pacienteCreateDto);


    @Mapping(source = "direccion.localidad", target = "localidad")
    @Mapping(source = "direccion.provincia", target = "provincia")
    PacienteDto toDto(Paciente paciente);

    Paciente toEntity(PacienteDto pacienteDto);
    List<PacienteDto> toListDto(List<Paciente> medicos);


    PacienteProfile toProfileDto(Paciente paciente);

}
