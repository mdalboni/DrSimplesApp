package overload.drmed.overload.drmed.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Dalbo on 07/04/2017.
 */

public class Paciente {

    private ArrayList<PacienteConvenios> PacienteConvenios;
    private String Nome_Paci;
    private String NomeSocial_Paci;
    private String DtNasc_Paci;
    private String Sexo_Paci;
    private String CPF_Paci;
    private String RG_Paci;
    private String Orgao_Paci;
    private String Email_Paci;
    private String CEP_Paci;
    private String Endereco_Paci;
    private String Numero_Paci;
    private String Complemento_Paci;
    private String Bairro_Paci;
    private String Cidade_Paci;
    private String Estado_Paci;
    private String Mae_Paci;
    private String Pai_Paci;
    private String Telefone_Paci;
    private String ID_Paci;


    public String getNome_Paci() {
        return Nome_Paci;
    }

    public void setNome_Paci(String nome_Paci) {
        Nome_Paci = nome_Paci;
    }

    public String getNomeSocial_Paci() {
        return NomeSocial_Paci;
    }

    public void setNomeSocial_Paci(String nomeSocial_Paci) {
        NomeSocial_Paci = nomeSocial_Paci;
    }

    public String getDtNasc_Paci() {
        return DtNasc_Paci;
    }

    public void setDtNasc_Paci(String dtNasc_Paci) {
        DtNasc_Paci = dtNasc_Paci;
    }

    public String getSexo_Paci() {
        return Sexo_Paci;
    }

    public void setSexo_Paci(String sexo_Paci) {
        Sexo_Paci = sexo_Paci;
    }

    public String getCPF_Paci() {
        return CPF_Paci;
    }

    public void setCPF_Paci(String CPF_Paci) {
        this.CPF_Paci = CPF_Paci;
    }

    public String getRG_Paci() {
        return RG_Paci;
    }

    public void setRG_Paci(String RG_Paci) {
        this.RG_Paci = RG_Paci;
    }

    public String getOrgao_Paci() {
        return Orgao_Paci;
    }

    public void setOrgao_Paci(String orgao_Paci) {
        Orgao_Paci = orgao_Paci;
    }

    public String getEmail_Paci() {
        return Email_Paci;
    }

    public void setEmail_Paci(String email_Paci) {
        Email_Paci = email_Paci;
    }

    public String getCEP_Paci() {
        return CEP_Paci;
    }

    public void setCEP_Paci(String CEP_Paci) {
        this.CEP_Paci = CEP_Paci;
    }

    public String getEndereco_Paci() {
        return Endereco_Paci;
    }

    public void setEndereco_Paci(String endereco_Paci) {
        Endereco_Paci = endereco_Paci;
    }

    public String getNumero_Paci() {
        return Numero_Paci;
    }

    public void setNumero_Paci(String numero_Paci) {
        Numero_Paci = numero_Paci;
    }

    public String getComplemento_Paci() {
        return Complemento_Paci;
    }

    public void setComplemento_Paci(String complemento_Paci) {
        Complemento_Paci = complemento_Paci;
    }

    public String getBairro_Paci() {
        return Bairro_Paci;
    }

    public void setBairro_Paci(String bairro_Paci) {
        Bairro_Paci = bairro_Paci;
    }

    public String getCidade_Paci() {
        return Cidade_Paci;
    }

    public void setCidade_Paci(String cidade_Paci) {
        Cidade_Paci = cidade_Paci;
    }

    public String getEstado_Paci() {
        return Estado_Paci;
    }

    public void setEstado_Paci(String estado_Paci) {
        Estado_Paci = estado_Paci;
    }

    public String getMae_Paci() {
        return Mae_Paci;
    }

    public void setMae_Paci(String mae_Paci) {
        Mae_Paci = mae_Paci;
    }

    public String getPai_Paci() {
        return Pai_Paci;
    }

    public void setPai_Paci(String pai_Paci) {
        Pai_Paci = pai_Paci;
    }

    public String getTelefone_Paci() {
        return Telefone_Paci;
    }

    public void setTelefone_Paci(String telefone_Paci) {
        Telefone_Paci = telefone_Paci;
    }

    public String getID_Paci() {
        return ID_Paci;
    }

    public void setID_Paci(String ID_Paci) {
        this.ID_Paci = ID_Paci;
    }

    public ArrayList<PacienteConvenios> getPacienteConvenios() {
        return PacienteConvenios;
    }

    public void setPacienteConvenios(ArrayList<PacienteConvenios> pacienteConvenios) {
        PacienteConvenios = pacienteConvenios;
    }
}
