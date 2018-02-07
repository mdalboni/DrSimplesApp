package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

/**
 * Created by Dalbo on 13/04/2017.
 */

public class PesquisaPacienteConvenio {

    boolean success;
    ArrayList<Pesquisa> PacienteConvenios;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Pesquisa> getPacienteConvenios() {
        return PacienteConvenios;
    }

    public void setPacienteConvenios(ArrayList<Pesquisa> pacienteConvenios) {
        PacienteConvenios = pacienteConvenios;
    }

    public class Pesquisa{
        String Nome_Paciente;
        String ID_PaciConv;
        String ID_Convenio;
        String Nome_Convenio;
        String Codigo;
        int Ativo;

        public String getID_Convenio() {
            return ID_Convenio;
        }

        public void setID_Convenio(String ID_Convenio) {
            this.ID_Convenio = ID_Convenio;
        }

        public String getID_PaciConv() {
            return ID_PaciConv;
        }

        public void setID_PaciConv(String ID_PaciConv) {
            this.ID_PaciConv = ID_PaciConv;
        }

        public void setCodigo(String codigo) {
            Codigo = codigo;
        }

        public String getCodigo() {
            return Codigo;
        }

        public int getAtivo() {
            return Ativo;
        }

        public void setAtivo(int ativo) {
            Ativo = ativo;
        }

        public String getNome_Convenio() {
            return Nome_Convenio;
        }

        public String getNome_Paciente() {
            return Nome_Paciente;
        }

        public void setNome_Convenio(String nome_Convenio) {
            Nome_Convenio = nome_Convenio;
        }

        public void setNome_Paciente(String nome_Paciente) {
            Nome_Paciente = nome_Paciente;
        }
    }

}


