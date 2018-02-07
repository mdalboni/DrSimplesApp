package overload.drmed.overload.drmed.models;

/**
 * Created by Dalbo on 13/04/2017.
 */

public class CadastrarConvenio {

    private int Id_Paci;
    private int Id_Conv;
    private String Codigo;
    private int ativo;

    public int getId_Paci() {
        return Id_Paci;
    }

    public void setId_Paci(int id_Paci) {
        Id_Paci = id_Paci;
    }

    public int getId_Conv() {
        return Id_Conv;
    }

    public void setId_Conv(int id_Conv) {
        Id_Conv = id_Conv;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
}
