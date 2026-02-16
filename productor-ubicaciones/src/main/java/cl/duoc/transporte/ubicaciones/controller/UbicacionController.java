package cl.duoc.transporte.ubicaciones.controller;

import cl.duoc.transporte.ubicaciones.model.UbicacionEntity;
import cl.duoc.transporte.ubicaciones.repository.UbicacionRepository;
import cl.duoc.transporte.ubicaciones.service.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicationService;

    @Autowired
    private UbicacionRepository repository;

    @GetMapping
    public List<UbicacionEntity> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public UbicacionEntity create(@RequestBody UbicacionEntity ubicacion) {
        if (ubicacion.getTimestamp() == null) {
            ubicacion.setTimestamp(LocalDateTime.now().toString());
        }
        UbicacionEntity saved = repository.save(ubicacion);
        ubicationService.enviarUbicacionManual(saved);
        return saved;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UbicacionEntity> update(@PathVariable Long id, @RequestBody UbicacionEntity detalles) {
        Optional<UbicacionEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            UbicacionEntity existing = optional.get();
            existing.setIdVehiculo(detalles.getIdVehiculo());
            existing.setLatitud(detalles.getLatitud());
            existing.setLongitud(detalles.getLongitud());
            UbicacionEntity updated = repository.save(existing);
            ubicationService.enviarUbicacionManual(updated);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<UbicacionEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            UbicacionEntity deleted = optional.get();
            ubicationService.enviarUbicacionManual(deleted);
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
