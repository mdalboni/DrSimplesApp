package overload.drmed.overload.drmed.controller;

import java.util.ArrayList;

import overload.drmed.overload.drmed.models.SuccessLog;

/**
 * Created by Dalbo on 03/05/2017.
 */

public class Laboratorios extends SuccessLog {

    ArrayList<Laboratorio> Laboratorio;

    public ArrayList<Laboratorios.Laboratorio> getLaboratorio() {
        return Laboratorio;
    }

    public void setLaboratorio(ArrayList<Laboratorios.Laboratorio> laboratorio) {
        Laboratorio = laboratorio;
    }

    public class Laboratorio {
        private String ID_Labo;
        private String ID_ConvLabo;
        private String Nome_Labo;
        private String CNPJ_Labo;
        private String CEP_Labo;
        private String Endereco_Labo;
        private String Numero_Labo;
        private String Complemento_Labo;
        private String Bairro_Labo;
        private String Cidade_Labo;
        private String Estado_Labo;
        private String Responsavel_Labo;
        private String Email_Labo;
        private String Id_Tipo;

        public void setID_ConvLabo(String ID_ConvLabo) {
            this.ID_ConvLabo = ID_ConvLabo;
        }

        public String getID_ConvLabo() {
            return ID_ConvLabo;
        }

        public String getID_Labo() {
            return ID_Labo;
        }

        public void setID_Labo(String ID_Labo) {
            this.ID_Labo = ID_Labo;
        }

        public String getNome_Labo() {
            return Nome_Labo;
        }

        public void setNome_Labo(String nome_Labo) {
            Nome_Labo = nome_Labo;
        }

        public String getCNPJ_Labo() {
            return CNPJ_Labo;
        }

        public void setCNPJ_Labo(String CNPJ_Labo) {
            this.CNPJ_Labo = CNPJ_Labo;
        }

        public String getCEP_Labo() {
            return CEP_Labo;
        }

        public void setCEP_Labo(String CEP_Labo) {
            this.CEP_Labo = CEP_Labo;
        }

        public String getEndereco_Labo() {
            return Endereco_Labo;
        }

        public void setEndereco_Labo(String endereco_Labo) {
            Endereco_Labo = endereco_Labo;
        }

        public String getNumero_Labo() {
            return Numero_Labo;
        }

        public void setNumero_Labo(String numero_Labo) {
            Numero_Labo = numero_Labo;
        }

        public String getComplemento_Labo() {
            return Complemento_Labo;
        }

        public void setComplemento_Labo(String complemento_Labo) {
            Complemento_Labo = complemento_Labo;
        }

        public String getBairro_Labo() {
            return Bairro_Labo;
        }

        public void setBairro_Labo(String bairro_Labo) {
            Bairro_Labo = bairro_Labo;
        }

        public String getCidade_Labo() {
            return Cidade_Labo;
        }

        public void setCidade_Labo(String cidade_Labo) {
            Cidade_Labo = cidade_Labo;
        }

        public String getEstado_Labo() {
            return Estado_Labo;
        }

        public void setEstado_Labo(String estado_Labo) {
            Estado_Labo = estado_Labo;
        }

        public String getResponsavel_Labo() {
            return Responsavel_Labo;
        }

        public void setResponsavel_Labo(String responsavel_Labo) {
            Responsavel_Labo = responsavel_Labo;
        }

        public String getEmail_Labo() {
            return Email_Labo;
        }

        public void setEmail_Labo(String email_Labo) {
            Email_Labo = email_Labo;
        }

        public String getId_Tipo() {
            return Id_Tipo;
        }

        public void setId_Tipo(String id_Tipo) {
            Id_Tipo = id_Tipo;
        }
    }
}
