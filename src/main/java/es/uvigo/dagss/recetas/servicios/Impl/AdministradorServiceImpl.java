package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.AdministradorDto;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.repositorios.AdministradorRepository;
import es.uvigo.dagss.recetas.servicios.AdministradorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository administradorRepository;

    public AdministradorServiceImpl(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Override
    public List<AdministradorDto> listarAdministradoresActivos() {
        // Obtener los administradores activos desde el repositorio
        List<Administrador> administradoresActivos = administradorRepository.findByActivoTrue();

        // Mapear las entidades a DTOs
        return administradoresActivos.stream()
                .map(admin -> new AdministradorDto(
                        admin.getNombre(),
                        admin.getEmail(),
                        admin.getFechaCreacion()
                ))
                .collect(Collectors.toList());
    }
}
