package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.PacienteDto;
import es.uvigo.dagss.recetas.dtos.PacienteProfile;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    @Mapping(source = "direccion.localidad", target = "localidad")
    @Mapping(source = "direccion.provincia", target = "provincia")
    @Mapping(source = "centroSalud", target = "centro" , qualifiedByName = "concatCentroSalud")
    PacienteDto toDto(Paciente paciente);

    Paciente toEntity(PacienteDto pacienteDto);
    List<PacienteDto> toListDto(List<Paciente> medicos);


    PacienteProfile toProfileDto(Paciente paciente);

    @Named("concatCentroSalud")
    default String concatCentroSalud(CentroSalud centroSalud) {
        if (centroSalud == null) {
            return null;
        }
        return centroSalud.getNombre() + " (" +  centroSalud.getDireccion().toString() + ")";
    }
}
