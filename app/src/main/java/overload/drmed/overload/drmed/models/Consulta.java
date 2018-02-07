package overload.drmed.overload.drmed.models;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Dalbo on 21/02/2017.
 */

public class Consulta {

    private String nome;
    private String data;
    private String protocolo;
    private String senha;
    private String url;




    public Consulta(String nome,String data,String protocolo,String senha,String url){
        this.nome=nome;
        this.data=data;
        this.protocolo=protocolo;
        this.senha=senha;
        this.url=url;
    }

    public String getNome(){
        return nome;
    }

    public String getUrl(){
        return url;
    }

    public String getData(){
        return data;
    }

    public String getProtocolo(){
        return protocolo;
    }

    public String getSenha(){
        return senha;
    }

    public void setNome(String nome){
        this.nome=nome;
    }

    public void setData(String data){
        this.data=data;
    }

    public void setProtocolo(String protocolo){
        this.protocolo=protocolo;
    }

    public void setSenha(String senha){
        this.senha=senha;
    }

    public void setUrl(String url){
        this.url=url;
    }

}
