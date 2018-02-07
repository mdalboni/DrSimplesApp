package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

/**
 * Created by Dalbo on 20/04/2017.
 */

public class ConsultorioConvenio {

    boolean success;
    ArrayList<ConsConv> ConsConv;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setConsConv(ArrayList<ConsultorioConvenio.ConsConv> consConv) {
        ConsConv = consConv;
    }

    public ArrayList<ConsultorioConvenio.ConsConv> getConsConv() {
        return ConsConv;
    }


    public class ConsConv {
        private String Id_ConsuConv;
        private String ativo;
        private String ID_Consu;
        private String Nome_Consu;
        private String Numero_Consu;
        private String Responsavel_Consu;
        private String Telefone_Consu;
        private String CNPJ_Consu;
        private String CEP_Consu;
        private String Cidade_Consu;
        private String Endereco_Consu;
        private String Complemento_Consu;
        private String Bairro_Consu;
        private String Id_Conv;
        private String Nome_Conv;
        private String Id_Medi;
        private String Nome_Medi;
        private String CPF_Medi;
        private String CRM_Medi;
        private String Email_Medi;
        private String Telefone_Medi;
        private String TipoInscri_Medi;


        public String getId_ConsuConv() {
            return Id_ConsuConv;
        }

        public void setId_ConsuConv(String id_ConsuConv) {
            Id_ConsuConv = id_ConsuConv;
        }

        public String getAtivo() {
            return ativo;
        }

        public void setAtivo(String ativo) {
            this.ativo = ativo;
        }

        public String getID_Consu() {
            return ID_Consu;
        }

        public void setID_Consu(String ID_Consu) {
            this.ID_Consu = ID_Consu;
        }

        public String getNome_Consu() {
            return Nome_Consu;
        }

        public void setNome_Consu(String nome_Consu) {
            Nome_Consu = nome_Consu;
        }

        public String getNumero_Consu() {
            return Numero_Consu;
        }

        public void setNumero_Consu(String numero_Consu) {
            Numero_Consu = numero_Consu;
        }

        public String getResponsavel_Consu() {
            return Responsavel_Consu;
        }

        public void setResponsavel_Consu(String responsavel_Consu) {
            Responsavel_Consu = responsavel_Consu;
        }

        public String getTelefone_Consu() {
            return Telefone_Consu;
        }

        public void setTelefone_Consu(String telefone_Consu) {
            Telefone_Consu = telefone_Consu;
        }

        public String getCNPJ_Consu() {
            return CNPJ_Consu;
        }

        public void setCNPJ_Consu(String CNPJ_Consu) {
            this.CNPJ_Consu = CNPJ_Consu;
        }

        public String getCEP_Consu() {
            return CEP_Consu;
        }

        public void setCEP_Consu(String CEP_Consu) {
            this.CEP_Consu = CEP_Consu;
        }

        public String getCidade_Consu() {
            return Cidade_Consu;
        }

        public void setCidade_Consu(String cidade_Consu) {
            Cidade_Consu = cidade_Consu;
        }

        public String getEndereco_Consu() {
            return Endereco_Consu;
        }

        public void setEndereco_Consu(String endereco_Consu) {
            Endereco_Consu = endereco_Consu;
        }

        public String getComplemento_Consu() {
            return Complemento_Consu;
        }

        public void setComplemento_Consu(String complemento_Consu) {
            Complemento_Consu = complemento_Consu;
        }

        public String getBairro_Consu() {
            return Bairro_Consu;
        }

        public void setBairro_Consu(String bairro_Consu) {
            Bairro_Consu = bairro_Consu;
        }

        public String getId_Conv() {
            return Id_Conv;
        }

        public void setId_Conv(String id_Conv) {
            Id_Conv = id_Conv;
        }

        public String getNome_Conv() {
            return Nome_Conv;
        }

        public void setNome_Conv(String nome_Conv) {
            Nome_Conv = nome_Conv;
        }

        public String getId_Medi() {
            return Id_Medi;
        }

        public void setId_Medi(String id_Medi) {
            Id_Medi = id_Medi;
        }

        public String getNome_Medi() {
            return Nome_Medi;
        }

        public void setNome_Medi(String nome_Medi) {
            Nome_Medi = nome_Medi;
        }

        public String getCPF_Medi() {
            return CPF_Medi;
        }

        public void setCPF_Medi(String CPF_Medi) {
            this.CPF_Medi = CPF_Medi;
        }

        public String getCRM_Medi() {
            return CRM_Medi;
        }

        public void setCRM_Medi(String CRM_Medi) {
            this.CRM_Medi = CRM_Medi;
        }

        public String getEmail_Medi() {
            return Email_Medi;
        }

        public void setEmail_Medi(String email_Medi) {
            Email_Medi = email_Medi;
        }

        public String getTelefone_Medi() {
            return Telefone_Medi;
        }

        public void setTelefone_Medi(String telefone_Medi) {
            Telefone_Medi = telefone_Medi;
        }

        public String getTipoInscri_Medi() {
            return TipoInscri_Medi;
        }

        public void setTipoInscri_Medi(String tipoInscri_Medi) {
            TipoInscri_Medi = tipoInscri_Medi;
        }
    }
}
