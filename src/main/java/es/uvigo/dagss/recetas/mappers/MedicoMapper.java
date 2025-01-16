package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.MedicoDto;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface MedicoMapper {
    MedicoDto toDto(Medico medico);
    Medico toEntity(MedicoDto medicoDto);
    List<MedicoDto> toListDto(List<Medico> medicos);
}
