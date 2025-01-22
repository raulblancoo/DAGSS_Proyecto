package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.MedicoDto;
import es.uvigo.dagss.recetas.dtos.MedicoProfile;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel="spring")
public interface MedicoMapper {
    @Mapping(source = "direccion.localidad", target = "localidad")
    @Mapping(source = "direccion.provincia", target = "provincia")
    @Mapping(source = "centroSalud", target = "centro" , qualifiedByName = "concatCentroSalud")
    MedicoDto toDto(Medico medico);
    List<MedicoDto> toListDto(List<Medico> medicos);


    MedicoProfile toProfileDto(Medico medico);


    @Named("concatCentroSalud")
    default String concatCentroSalud(CentroSalud centroSalud) {
        if (centroSalud == null) {
            return null;
        }
        return centroSalud.getNombre() + " (" +  centroSalud.getDireccion().toString() + ")";
    }

}
