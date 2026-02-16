package cl.duoc.transporte.consumidor.ubicaciones.repository;

import cl.duoc.transporte.consumidor.ubicaciones.model.UbicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<UbicacionEntity, Long> {
}
