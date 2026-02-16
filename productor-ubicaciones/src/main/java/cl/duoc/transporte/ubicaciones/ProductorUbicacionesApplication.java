package cl.duoc.transporte.ubicaciones;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProductorUbicacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductorUbicacionesApplication.class, args);
    }

}
