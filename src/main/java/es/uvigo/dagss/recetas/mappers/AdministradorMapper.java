package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.AdministradorDto;
import es.uvigo.dagss.recetas.entidades.Administrador;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface AdministradorMapper {
    AdministradorDto toDto(Administrador administrador);
    List<AdministradorDto> toListDto(List<Administrador> administradores);
}
