package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.DireccionDto;
import es.uvigo.dagss.recetas.dtos.UpdateFarmaciaProfileRequest;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.FarmaciaRepository;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmaciaServiceImpl implements FarmaciaService {
    private final FarmaciaRepository farmaciaRepository;

    public FarmaciaServiceImpl(FarmaciaRepository farmaciaRepository) {
        this.farmaciaRepository = farmaciaRepository;
    }

    @Override
    public List<Farmacia> buscarFarmaciasConFiltros(String nombreEstablecimiento, String localidad) {
        if(nombreEstablecimiento == null && localidad == null) {
            return farmaciaRepository.findAll();
        } else if (nombreEstablecimiento != null && localidad == null) {
            return farmaciaRepository.findByNombreEstablecimientoLike(nombreEstablecimiento);
        } else if(nombreEstablecimiento == null && localidad != null) {
            return farmaciaRepository.findByDireccion_LocalidadLike(localidad);
        } else {
            throw new WrongParameterException("Solo se puede proporcionar un parámetro de filtro a la vez.");
        }
    }

    @Transactional
    @Override
    public Farmacia crearFarmacia(Farmacia farmacia) {
        if(farmaciaRepository.existsByDniOrNumeroColegiado(farmacia.getDni(), farmacia.getNumeroColegiado())) {
            throw new ResourceAlreadyExistsException("Ya existe una farmacia con los datos proporcionados.");
        }

        return farmaciaRepository.save(farmacia);
    }

    @Override
    public Farmacia editarFarmacia(Long id, Farmacia farmacia) {
        return null;
    }

    @Transactional
    @Override
    public void eliminarFarmacia(Long id) {
        Farmacia farmaciaExistente = farmaciaRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No existe la farmacia con id:" + id));
        farmaciaExistente.desactivar();
        farmaciaRepository.save(farmaciaExistente);
    }

    @Override
    public List<String> getHomeOptions() {
        return List.of("Gestión de recetas", "Mi perfil", "Desconectar");
    }

    @Override
    public Farmacia getPerfil(String numeroColegiado) {
        return getCurrentFarmacia(numeroColegiado);
    }

    @Transactional
    @Override
    public void updatePerfil(UpdateFarmaciaProfileRequest request, String numeroColegiado) {
        Farmacia farmacia = getCurrentFarmacia(numeroColegiado);

        farmacia.setNombreFarmaceutico(request.getNombre());
        farmacia.setTelefono(request.getTelefono());
        farmacia.setEmail(request.getEmail());

        Direccion direccion = farmacia.getDireccion();
        DireccionDto direccionDto = request.getDireccion();
        if (direccionDto != null) {
            direccion.setDomicilio(direccionDto.getDomicilio());
            direccion.setLocalidad(direccionDto.getLocalidad());
            direccion.setCodigoPostal(direccionDto.getCodigoPostal());
            direccion.setProvincia(direccionDto.getProvincia());
        }

        farmaciaRepository.save(farmacia);
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequest request, String numeroColegiado) {
        Farmacia farmacia = getCurrentFarmacia(numeroColegiado);

        //TODO: método cambiar contraseña
//        User user = medico.getUser();
//
//        // Verificar contraseña actual
//        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getContraseña())) {
//            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
//        }
//
//        // Verificar que la nueva contraseña y la confirmación coinciden
//        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
//            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden.");
//        }
//
//        // Encriptar y actualizar la contraseña
//        user.setContraseña(passwordEncoder.encode(request.getNewPassword()));
//        userRepository.save(user);
    }

    @Override
    public void servirReceta(Long receta_id, Long farmaciaId) {
//        Farmacia farmacia = farmaciaRepository.findById(farmaciaId)
//                .orElseThrow(() -> new ResourceNotFoundException("Farmacia no encontrada."));
//
//        Receta receta = recetaRepository.findById(receta_id)
//                .orElseThrow(() -> new IllegalArgumentException("Receta no encontrada."));
//
//        if (receta.getEstado() != Receta.Estado.PLANIFICADA) {
//            throw new IllegalArgumentException("Solo se pueden servir recetas en estado PLANIFICADA.");
//        }
//
//        // Vincula la receta con la farmacia y actualiza el estado
//        receta.setFarmacia(farmacia);
//        receta.setEstado(Receta.Estado.SERVIDA);
//        recetaRepository.save(receta);
    }

    @Override
    public Farmacia findFarmaciaById(Long id) {
        // TODO: comprobar el uso de Optional
        Optional<Farmacia> farmacia = farmaciaRepository.findById(id);
        return farmacia.orElseGet(Farmacia::new);
    }

    private Farmacia getCurrentFarmacia(String numeroColegiado) {
        return farmaciaRepository.findByNumeroColegiado(numeroColegiado);
    }
}
