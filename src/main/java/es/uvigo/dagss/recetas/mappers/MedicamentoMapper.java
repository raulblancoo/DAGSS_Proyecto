package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.MedicamentoDto;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface MedicamentoMapper {
    MedicamentoDto toDto(Medicamento medicamento);
    Medicamento toEntity(MedicamentoDto medicamentoDto);
    List<MedicamentoDto> toListDto(List<Medicamento> medicamentos);
}
