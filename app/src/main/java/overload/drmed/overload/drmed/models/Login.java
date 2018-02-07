package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

/**
 * Created by Dalbo on 12/04/2017.
 */

public class Login {

    private boolean success;
    private ArrayList<Usuario> Usuario;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Usuario> getUsuario() {
        return Usuario;
    }

    public void setUsuario(ArrayList<Usuario> usuario) {
        Usuario = usuario;
    }

    public class Usuario {
        private int ID_UsuPaci;
        private String Nome_Paci;
        private String Email_Paci;
        private int ID_Paci;
        private String Senha_UsuPaci;
        private String Usuario_UsuPaci;

        public String getSenha_UsuPaci() {
            return Senha_UsuPaci;
        }

        public String getUsuario_UsuPaci() {
            return Usuario_UsuPaci;
        }

        public void setSenha_UsuPaci(String senha_UsuPaci) {
            Senha_UsuPaci = senha_UsuPaci;
        }

        public void setUsuario_UsuPaci(String usuario_UsuPaci) {
            Usuario_UsuPaci = usuario_UsuPaci;
        }

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
        }
    }

}

