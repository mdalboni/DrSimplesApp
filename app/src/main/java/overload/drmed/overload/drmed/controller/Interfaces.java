package overload.drmed.overload.drmed.controller;

import overload.drmed.overload.drmed.models.Areas;
import overload.drmed.overload.drmed.models.CadastrarConvenio;
import overload.drmed.overload.drmed.models.ConsultorioConvenio;
import overload.drmed.overload.drmed.models.Endereco;
import overload.drmed.overload.drmed.models.Exame;
import overload.drmed.overload.drmed.models.ListaHorarios;
import overload.drmed.overload.drmed.models.Login;
import overload.drmed.overload.drmed.models.PacConsulta;
import overload.drmed.overload.drmed.models.PacCreate;
import overload.drmed.overload.drmed.models.PesquisaConvenio;
import overload.drmed.overload.drmed.models.PesquisaPacienteConvenio;
import overload.drmed.overload.drmed.models.RetornoCadConvenio;
import overload.drmed.overload.drmed.models.SPaciente;
import overload.drmed.overload.drmed.models.Success;
import overload.drmed.overload.drmed.models.SuccessLog;
import overload.drmed.overload.drmed.models.UsuarioPaciente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Dalbo on 13/04/2017.
 */

public class Interfaces {

    public interface PesqConvenio {
        @POST("/Convenio/getConvenio")
        Call<PesquisaConvenio> getConvenios();
    }

    public interface CadConvenio {
        @POST("/PacienteConvenio/CreateJSON")
        Call<RetornoCadConvenio> setConvenios(@Body CadastrarConvenio paciente);
    }

    public interface PacienteConvenio {
        @FormUrlEncoded
        @POST("/PacienteConvenio/getConvenio")
        Call<PesquisaPacienteConvenio> getConvenios(@Field("ID_Paci") String ID_Paci);
    }

    public interface EditarPacienteConvenio {
        @FormUrlEncoded
        @POST("/PacienteConvenio/EditJSON")
        Call<Success> getConvenios(@Field("ID_PaciConv") String ID_Paci, @Field("Ativo") String ativo);
    }

    public interface PegarPaciente {
        @FormUrlEncoded
        @POST("/Paciente/GetPaciente")
        Call<SPaciente> getPaciente(@Field("ID_Paci") int ID_Paci);
    }

    public interface CriarPaciente {
        @FormUrlEncoded
        @POST("/Paciente/CreateJSON")
        Call<PacCreate> criar(@Field("Nome_Paci") String nome,
                              @Field("NomeSocial_Paci") String nomes,
                              @Field("DtNasc_Paci") String dt,
                              @Field("Sexo_Paci") String sexo,
                              @Field("CPF_Paci") String cpf,
                              @Field("RG_Paci") String rg,
                              @Field("Orgao_Paci") String orgao,
                              @Field("Email_Paci") String email,
                              @Field("CEP_Paci") String cep,
                              @Field("Endereco_Paci") String endereco,
                              @Field("Numero_Paci") String numero,
                              @Field("Complemento_Paci") String comple,
                              @Field("Bairro_Paci") String bairro,
                              @Field("Cidade_Paci") String cidade,
                              @Field("Estado_Paci") String estado,
                              @Field("Mae_Paci") String mae,
                              @Field("Pai_Paci") String pai,
                              @Field("Telefone_Paci") String tel,
                              @Field("Senha_UsuPaci") String senha);
    }

    public interface EditarPaciente {
        @FormUrlEncoded
        @POST("/Paciente/EditJSON")
        Call<SPaciente> editarPaciente(@Field("ID_Paci") String id,
                                       @Field("Nome_Paci") String nome,
                                       @Field("NomeSocial_Paci") String nomes,
                                       @Field("DtNasc_Paci") String dt,
                                       @Field("Sexo_Paci") String sexo,
                                       @Field("CPF_Paci") String cpf,
                                       @Field("RG_Paci") String rg,
                                       @Field("Orgao_Paci") String orgao,
                                       @Field("Email_Paci") String email,
                                       @Field("CEP_Paci") String cep,
                                       @Field("Endereco_Paci") String endereco,
                                       @Field("Numero_Paci") String numero,
                                       @Field("Complemento_Paci") String comple,
                                       @Field("Bairro_Paci") String bairro,
                                       @Field("Cidade_Paci") String cidade,
                                       @Field("Estado_Paci") String estado,
                                       @Field("Mae_Paci") String mae,
                                       @Field("Pai_Paci") String pai,
                                       @Field("Telefone_Paci") String tel);

    }


    public interface EditarSenha {
        @FormUrlEncoded
        @POST("/UsuarioPaciente/EditJSON")
        Call<Object> setSenha(@Field("Usuario_UsuPaci") String Usuario_UsuPaci, @Field("Senha_UsuPaci") String Senha_UsuPaci);
    }

    public interface ViaCep {
        @GET("/ws/{cep}/json")
        Call<Endereco> endereco(
                @Path("cep") String cep);
    }



    public interface UsuarioLogin {
        @POST("/UsuarioPaciente/LoginJson")
        Call<Login> createUser(@Body UsuarioPaciente Usuario);
    }

    public interface PegarArea {
        @POST("/Area/GetAreas")
        Call<Areas> getAreas();
    }


    public interface PesquisarMedicoConsultorio {
        @FormUrlEncoded
        @POST("/ConsultorioConvenios/GetConsulConv")
        Call<ConsultorioConvenio> getMedicoConsultori(@Field("ID_Convenio") int idconv,
                                                      @Field("ID_Area") int idarea,
                                                      @Field("Cidade") String cidade,
                                                      @Field("Nom_MedCons") String nome);
    }

    public  interface PesquisarConsulta{
        @FormUrlEncoded
        @POST("/Paciente/GetConsulta")
        Call<PacConsulta> getConsultaPaciente(@Field("ID_Paci") String ID_Paci, @Field("tipoConsulta") String tipoConsulta);
    }

    public interface  PesquisarHorariosConsulta{
        @FormUrlEncoded
        @POST("/ConsultorioConvenios/GetConvConsult")
        Call<ListaHorarios> getListaHorarios(@Field("ID_ConsuConv") String ID_ConsuConv,@Field("data") String data);
    }

    public interface CadastrarConsulta{
        @FormUrlEncoded
        @POST("/Consulta/CreateJSON")
        Call<Success> criarConsulta(@Field("Data_Consa") String Data_Consa,
                                       @Field("Id_ConsuConv") String Id_ConsuConv,
                                       @Field("Id_PaciConv") String Id_PaciConv);
    }

    public interface CancelarConsulta{
        @FormUrlEncoded
        @POST("/Consulta/CancelaConsulta")
        Call<Success> cancelarConsulta(@Field("ID_Consa")int ID_Consa);
    }

    public interface PesquisarExames{
        @FormUrlEncoded
        @POST("/Exame/GetExame")
        Call<Exame> getExames(@Field("Id_Paci")String ID_Consa, @Field("tipoConsulta")String tipoConsulta);
    }

    public interface ConsultarLaboratorios{
        @FormUrlEncoded
        @POST("/Laboratorio/GetLaboratorio")
        Call<Laboratorios> getLaboratorios(@Field("Cidade")String Cidade, @Field("Nome")String Nome,@Field("ID_Conv") String ID);
    }

    public interface CadastrarExame{
        @FormUrlEncoded
        @POST("/Exame/CadastrarExame")
        Call<Success> criarExame(@Field("Entrega") String Entrega,
                                 @Field("ID_PaciConv") String ID_PaciConv,
                                 @Field("Id_ConvLabo") String Id_Laboratorio,
                                 @Field("Protocolo") String Protocolo,
                                 @Field("SenhaProtocolo") String SenhaProtocolo
                                 );
    }

    public interface CancelarExames{
        @FormUrlEncoded
        @POST("/Exame/DeleteExame")
        Call<Success> cancelarExame(@Field("ID_Exame")int ID_Exame);
    }




}
