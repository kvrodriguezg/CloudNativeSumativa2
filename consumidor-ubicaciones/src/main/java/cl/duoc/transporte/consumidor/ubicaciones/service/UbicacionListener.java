package cl.duoc.transporte.consumidor.ubicaciones.service;

import cl.duoc.transporte.consumidor.ubicaciones.model.UbicacionEntity;
import cl.duoc.transporte.consumidor.ubicaciones.repository.UbicacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionListener {

    private static final Logger log = LoggerFactory.getLogger(UbicacionListener.class);

    @Autowired
    private UbicacionRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "${app.rabbitmq.queue.ubicaciones}")
    public void recibirMensaje(String mensaje) {
        log.info("Mensaje recibido: {}", mensaje);
        try {
            //Deserializar JSON
            UbicacionEntity entity = objectMapper.readValue(mensaje, UbicacionEntity.class);
            entity.setId(null);

            repository.save(entity);
            log.info("Ubicación guardada en Oracle Cloud ID: {}", entity.getId());

        } catch (Exception e) {
            log.error("Error procesando mensaje: {}", e.getMessage(), e);
        }
    }
}
