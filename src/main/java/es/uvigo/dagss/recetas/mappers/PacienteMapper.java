package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.MedicoDto;
import es.uvigo.dagss.recetas.dtos.PacienteCreateDto;
import es.uvigo.dagss.recetas.dtos.PacienteDto;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
//
//    @Mapping(source = "centroSalud.id", target = "centroSalud")
//    PacienteDto toDto(Paciente paciente);
//
//    List<PacienteDto> toListDto(List<Paciente> pacientes);
//
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(pacienteCreateDto.getDni())")
    @Mapping(source = "centroSaludId", target = "centroSalud.id")
    @Mapping(source = "medicoId", target = "medico.id")
    Paciente toEntity(PacienteCreateDto pacienteCreateDto);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(source = "centroSaludId", target = "centroSalud.id")
//    @Mapping(source = "medicoId", target = "medico.id")
//    void updatePacienteFromDto(PacienteUpdateDto pacienteUpdateDto, @MappingTarget Paciente paciente);

    PacienteDto toDto(Paciente paciente);
    Paciente toEntity(PacienteDto pacienteDto);
    List<PacienteDto> toListDto(List<Paciente> medicos);
}
