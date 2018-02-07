package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

/**
 * Created by Dalbo on 03/05/2017.
 */

public class Exame extends SuccessLog {

    ArrayList<exames> exames;

    public ArrayList<Exame.exames> getExames() {
        return exames;
    }

    public void setExames(ArrayList<Exame.exames> exames) {
        this.exames = exames;
    }


    public class exames {
        String Id_Exame;
        String Protocolo;
        String SenhaProtocolo;
        String Laboratorio;
        String Data;
        boolean Realizado;
        String Entrega;
        String NomeArquivo;

        public void setNomeArquivo(String nomeArquivo) {
            NomeArquivo = nomeArquivo;
        }

        public String getNomeArquivo() {
            return NomeArquivo;
        }

        public String getData() {
            return Data;
        }

        public String getEntrega() {
            return Entrega;
        }

        public String getId_Exame() {
            return Id_Exame;
        }

        public String getLaboratorio() {
            return Laboratorio;
        }

        public String getProtocolo() {
            return Protocolo;
        }

        public boolean getRealizado() {
            return Realizado;
        }

        public String getSenhaProtocolo() {
            return SenhaProtocolo;
        }

        public void setData(String data) {
            Data = data;
        }

        public void setEntrega(String entrega) {
            Entrega = entrega;
        }

        public void setId_Exame(String id_Exame) {
            Id_Exame = id_Exame;
        }

        public void setLaboratorio(String laboratorio) {
            Laboratorio = laboratorio;
        }

        public void setProtocolo(String protocolo) {
            Protocolo = protocolo;
        }

        public void setRealizado(boolean realizado) {
            Realizado = realizado;
        }

        public void setSenhaProtocolo(String senhaProtocolo) {
            SenhaProtocolo = senhaProtocolo;
        }
    }

}
