package cl.duoc.transporte.consumidor.horarios.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HorarioListener {

    @Value("${app.storage.path}")
    private String storagePath;

    @RabbitListener(queues = "${app.rabbitmq.queue.horarios}")
    public void recibirMensaje(String mensaje) {
        System.out.println("Actualización de horario recibida: " + mensaje);
        try {
            File directory = new File(storagePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "update_" + timestamp + ".json";
            File file = new File(directory, filename);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(mensaje);
            }

            System.out.println("Archivo guardado en: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error escribiendo archivo: " + e.getMessage());
        }
    }
}
