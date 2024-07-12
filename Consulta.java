import java.util.Date;

public class Consulta {

    private Long idPaciente;
    private String data;
    private String hora;
    private String especialidade;

    public Consulta(Long idPaciente, String data, String hora, String especialidade) {
        this.idPaciente = idPaciente;
        this.data = data;
        this.hora = hora;
        this.especialidade = especialidade;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}