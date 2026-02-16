package cl.duoc.transporte.horarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PROD_HORARIOS")
public class HorarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruta;
    private String nuevoHorario;
    private String motivo;
    private String timestamp;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNuevoHorario() {
        return nuevoHorario;
    }

    public void setNuevoHorario(String nuevoHorario) {
        this.nuevoHorario = nuevoHorario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
