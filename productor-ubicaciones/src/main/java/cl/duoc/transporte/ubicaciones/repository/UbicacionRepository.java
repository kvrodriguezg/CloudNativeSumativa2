package cl.duoc.transporte.ubicaciones.repository;
import cl.duoc.transporte.ubicaciones.model.UbicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<UbicacionEntity, Long> {
}
