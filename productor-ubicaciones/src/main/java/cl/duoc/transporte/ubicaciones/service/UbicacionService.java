package cl.duoc.transporte.ubicaciones.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UbicacionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.queue.ubicaciones}")
    private String queueName;

    private final Random random = new Random();

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    //Simular el envío de coordenadas cada 5 segundos
    public void enviarUbicacion() {
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("idVehiculo", "BUS-" + (random.nextInt(10) + 1));
        mensaje.put("latitud", -33.45 + (random.nextDouble() * 0.1));
        mensaje.put("longitud", -70.66 + (random.nextDouble() * 0.1));
        mensaje.put("timestamp", LocalDateTime.now().toString());

        try {
            String json = objectMapper.writeValueAsString(mensaje);
            System.out.println("Enviando ubicación automática: " + json);
            rabbitTemplate.convertAndSend(queueName, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarUbicacionManual(cl.duoc.transporte.ubicaciones.model.UbicacionEntity entity) {
        try {
            String json = objectMapper.writeValueAsString(entity);
            System.out.println("Enviando ubicación manual: " + json);
            rabbitTemplate.convertAndSend(queueName, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
