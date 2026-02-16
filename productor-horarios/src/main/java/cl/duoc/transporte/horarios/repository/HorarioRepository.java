package cl.duoc.transporte.horarios.repository;

import cl.duoc.transporte.horarios.model.HorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<HorarioEntity, Long> {
}
