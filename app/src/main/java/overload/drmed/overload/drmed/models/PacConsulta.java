package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

/**
 * Created by Dalbo on 25/04/2017.
 */

public class PacConsulta extends SuccessLog {

    ArrayList<Pacientes> Pacientes;

    public ArrayList<PacConsulta.Pacientes> getPacientes() {
        return Pacientes;
    }

    public void setPacientes(ArrayList<PacConsulta.Pacientes> pacientes) {
        Pacientes = pacientes;
    }


    public class Pacientes {
        int ID_Consa;
        String Nome_Paciente;
        String Notas;
        String Data;
        String Motivo;
        String Situacao;
        String Nome_Medico;
        String Nome_Consul;
        String Nome_Conv;
        String Area;

        public String getArea() {
            return Area;
        }

        public String getNome_Conv() {
            return Nome_Conv;
        }

        public void setArea(String area) {
            Area = area;
        }

        public void setNome_Conv(String nome_Conv) {
            Nome_Conv = nome_Conv;
        }

        public int getID_Consa() {
            return ID_Consa;
        }

        public void setID_Consa(int ID_Consa) {
            this.ID_Consa = ID_Consa;
        }

        public String getData() {
            return Data;
        }

        public String getMotivo() {
            return Motivo;
        }

        public String getNome_Consul() {
            return Nome_Consul;
        }

        public String getNome_Medico() {
            return Nome_Medico;
        }

        public String getNome_Paciente() {
            return Nome_Paciente;
        }

        public String getNotas() {
            return Notas;
        }

        public String getSituacao() {
            return Situacao;
        }

        public void setData(String data) {
            Data = data;
        }

        public void setMotivo(String motivo) {
            Motivo = motivo;
        }

        public void setNome_Consul(String nome_Consul) {
            Nome_Consul = nome_Consul;
        }

        public void setNome_Medico(String nome_Medico) {
            Nome_Medico = nome_Medico;
        }

        public void setNome_Paciente(String nome_Paciente) {
            Nome_Paciente = nome_Paciente;
        }

        public void setNotas(String notas) {
            Notas = notas;
        }

        public void setSituacao(String situacao) {
            Situacao = situacao;
        }
    }

}
