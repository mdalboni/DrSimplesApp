package overload.drmed.overload.drmed.models;

/**
 * Created by Dalbo on 20/04/2017.
 */

public class PacCreate extends SuccessLog{

    Paciente paciente;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente pac) {
        paciente = pac;
    }
}
