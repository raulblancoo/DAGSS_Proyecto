package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.CitaDto;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;



@Mapper(componentModel="spring")
public interface CitaMapper {
    @Mapping(source = "paciente", target = "paciente", qualifiedByName = "concatNombreApellidos")
    @Mapping(source = "paciente.fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "paciente", target = "direccion", qualifiedByName = "concatDireccion")
    @Mapping(source = "medico", target = "medico", qualifiedByName = "concatNombreApellidosMedico")
    @Mapping(source = "centroSalud.nombre", target = "centroSalud")
    CitaDto toDto(Cita cita);

    List<CitaDto> toListDto(List<Cita> citas);

    @Named("concatNombreApellidos")
    default String concatNombreApellidos(Paciente paciente) {
        return paciente.getNombre() + " " + paciente.getApellidos();
    }

    @Named("concatDireccion")
    default String concatDireccion(Paciente paciente) {
        return paciente.getDireccion().toString();
    }

    @Named("concatNombreApellidosMedico")
    default String concatNombreApellidosMedico(Medico medico) {
        return medico.getNombre() + " " + medico.getApellidos();
    }
}
