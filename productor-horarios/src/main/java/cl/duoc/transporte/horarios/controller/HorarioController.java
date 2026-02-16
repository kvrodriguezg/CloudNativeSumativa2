package cl.duoc.transporte.horarios.controller;

import cl.duoc.transporte.horarios.model.HorarioEntity;
import cl.duoc.transporte.horarios.repository.HorarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private HorarioRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.rabbitmq.queue.horarios}")
    private String queueName;

    @GetMapping
    public List<HorarioEntity> getAll() {
        return repository.findAll();
    }

    @PostMapping("/actualizar")
    public ResponseEntity<HorarioEntity> create(@RequestBody HorarioEntity horario) {
        if (horario.getTimestamp() == null) {
            horario.setTimestamp(LocalDateTime.now().toString());
        }

        HorarioEntity saved = repository.save(horario);

        try {
            String json = objectMapper.writeValueAsString(saved);
            System.out.println("Enviando horario: " + json);
            rabbitTemplate.convertAndSend(queueName, json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioEntity> update(@PathVariable Long id, @RequestBody HorarioEntity detalles) {
        Optional<HorarioEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            HorarioEntity existing = optional.get();
            existing.setRuta(detalles.getRuta());
            existing.setNuevoHorario(detalles.getNuevoHorario());
            existing.setMotivo(detalles.getMotivo());

            HorarioEntity updated = repository.save(existing);

            try {
                String json = objectMapper.writeValueAsString(updated);
                rabbitTemplate.convertAndSend(queueName, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<HorarioEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            HorarioEntity deleted = optional.get();
            try {
                String json = objectMapper.writeValueAsString(deleted);
                rabbitTemplate.convertAndSend(queueName, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
