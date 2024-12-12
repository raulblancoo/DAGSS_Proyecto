package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.dtos.AdministradorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdministradorService {
    List<AdministradorDto> listarAdministradoresActivos();
}
