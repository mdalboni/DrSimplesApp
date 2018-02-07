package overload.drmed.overload.drmed.models;

/**
 * Created by Dalbo on 20/04/2017.
 */

public class Medico {

    private String Nome_Area;
    private String Nome_Medi;
    private String CRM_Medi;
    private String Email_Medi;
    private String Telefone_Medi;
    private String TipoInscri_Medi;
    private String Ativo_Medi;


    public String getNome_Area() {
        return Nome_Area;
    }

    public void setNome_Area(String nome_Area) {
        Nome_Area = nome_Area;
    }

    public String getNome_Medi() {
        return Nome_Medi;
    }

    public void setNome_Medi(String nome_Medi) {
        Nome_Medi = nome_Medi;
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

    public String getAtivo_Medi() {
        return Ativo_Medi;
    }

    public void setAtivo_Medi(String ativo_Medi) {
        Ativo_Medi = ativo_Medi;
    }


}
