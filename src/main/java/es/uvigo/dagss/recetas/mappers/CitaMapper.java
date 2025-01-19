package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.CitaDto;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Mapper(componentModel="spring")
public interface CitaMapper {
    @Mapping(source = "paciente", target = "paciente", qualifiedByName = "concatNombreApellidos")
    @Mapping(source = "paciente.fechaNacimiento", target = "fechaNacimiento")
    @Mapping(source = "paciente", target = "direccion", qualifiedByName = "concatDireccion")
    @Mapping(source = "medico.nombre", target = "nombreMedico")
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

    //TODO: proceso de parsear la fecha a formato /dd/MM/yy
//    @Named("procesarFecha")
//    default String concatFecha(Paciente paciente) {
//        Instant instant = paciente.getFechaNacimiento().toInstant();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("UTC"));
//        return formatter.format(instant);
//    }

}
