package es.uvigo.dagss.recetas.servicios.Impl;

import es.uvigo.dagss.recetas.dtos.ChangePasswordRequest;
import es.uvigo.dagss.recetas.dtos.DireccionDto;
import es.uvigo.dagss.recetas.dtos.RecetaDto;
import es.uvigo.dagss.recetas.dtos.UpdateFarmaciaProfileRequest;
import es.uvigo.dagss.recetas.entidades.Direccion;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.excepciones.RecetaNoServibleException;
import es.uvigo.dagss.recetas.excepciones.ResourceAlreadyExistsException;
import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.excepciones.WrongParameterException;
import es.uvigo.dagss.recetas.repositorios.FarmaciaRepository;
import es.uvigo.dagss.recetas.repositorios.RecetaRepository;
import es.uvigo.dagss.recetas.servicios.FarmaciaService;
import es.uvigo.dagss.recetas.servicios.RecetaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class FarmaciaServiceImpl implements FarmaciaService {
    @Autowired
    private FarmaciaRepository farmaciaRepository;
    @Autowired
    private RecetaRepository recetaRepository;


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

        farmacia.setNombreEstablecimiento(request.getNombreEstablecimiento());
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
    public void servirReceta(Long recetaId, Long farmaciaId) {
        Farmacia farmacia = farmaciaRepository.findById(farmaciaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la farmacia con id:" + farmaciaId));

        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la receta con id:" + recetaId));

        if (receta.getEstado() != Receta.Estado.PLANIFICADA) {
            throw new IllegalArgumentException("Solo se pueden servir recetas en estado PLANIFICADA.");
        }

        Date now = new Date();
        if (receta.getFechaValidezInicial() != null && receta.getFechaValidezFinal() != null) {
            Date fechaInicial = Date.from(receta.getFechaValidezInicial().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaFinal = Date.from(receta.getFechaValidezFinal().atStartOfDay(ZoneId.systemDefault()).toInstant());

            if(fechaInicial.before(now) && fechaFinal.after(now)) {
                receta.setFarmacia(farmacia);
                receta.setEstado(Receta.Estado.SERVIDA);
                recetaRepository.save(receta);
            } else {
                throw new RecetaNoServibleException("Esta receta no se puede servir porque no está dentro de las fechas correctas.");            }
        } else {
            throw new RecetaNoServibleException("Esta receta no se puede servir porque no está dentro de las fechas correctas.");
        }
    }

    @Override
    public void puedeServirReceta(List<RecetaDto> recetas) {
        Date now = new Date();

        for(RecetaDto receta : recetas) {
            if (receta.getFechaValidezInicial() != null && receta.getFechaValidezFinal() != null) {
                receta.setPuedeSerServida(
                        !now.before(receta.getFechaValidezInicial()) && !now.after(receta.getFechaValidezFinal())
                );
            } else {
                receta.setPuedeSerServida(false);
            }
        }
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
