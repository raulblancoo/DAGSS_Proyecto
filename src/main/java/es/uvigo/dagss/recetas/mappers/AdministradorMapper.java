package es.uvigo.dagss.recetas.mappers;

import es.uvigo.dagss.recetas.dtos.AdministradorDto;
import es.uvigo.dagss.recetas.entidades.Administrador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface AdministradorMapper {
    @Mapping(source = "fechaAlta", target = "fechaCreacion", dateFormat = "dd/MM/yy")
    AdministradorDto toDto(Administrador administrador);
    List<AdministradorDto> toListDto(List<Administrador> administradores);
}
