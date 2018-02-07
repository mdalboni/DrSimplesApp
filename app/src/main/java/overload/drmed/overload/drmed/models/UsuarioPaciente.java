package overload.drmed.overload.drmed.models;

import android.content.Context;

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


import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Dalbo on 05/04/2017.
 */

public class UsuarioPaciente {

    private String Usuario_UsuPaci;
    private String Senha_UsuPaci;


    public String getUsuario_UsuPaci() {
        return Usuario_UsuPaci;
    }

    public void setUsuario_UsuPaci(String usuario_UsuPaci) {
        Usuario_UsuPaci = usuario_UsuPaci;
    }

    public String getSenha_UsuPaci() {
        return Senha_UsuPaci;
    }

    public void setSenha_UsuPaci(String senha_UsuPaci) {
        Senha_UsuPaci = senha_UsuPaci;
    }


    /*private int ID_UsuPaci;
    private String Nome_Paci;
    private String Email_Paci;
    private int ID_Paci;
    public int getID_UsuPaci() {
        return ID_UsuPaci;
    }

    public void setID_UsuPaci(int ID_UsuPaci) {
        this.ID_UsuPaci = ID_UsuPaci;
    }

    public String getNome_Paci() {
        return Nome_Paci;
    }

    public void setNome_Paci(String nome_Paci) {
        Nome_Paci = nome_Paci;
    }

    public String getEmail_Paci() {
        return Email_Paci;
    }

    public void setEmail_Paci(String email_Paci) {
        Email_Paci = email_Paci;
    }

    public int getID_Paci() {
        return ID_Paci;
    }

    public void setID_Paci(int ID_Paci) {
        this.ID_Paci = ID_Paci;
    }*/


}
