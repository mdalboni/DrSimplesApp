package overload.drmed.overload.drmed.models;

/**
 * Created by Dalbo on 07/04/2017.
 */

public class StaticFiles {


    private static String urlCEP = "https://viacep.com.br";
    private static String urlServer = "http://drmedapp.azurewebsites.net";
    private static String urlAPIEmail = "http://apilayer.net";

    private static String APIEmailKey = "e7ce546cc08faa8e190a54984337e7ed";

    private static String idPaciente = "";
    private static Paciente paciente;
    private static Endereco endereco;


    public static String getIdPaciente() {
        return idPaciente;
    }

    public static void setIdPaciente(String idPaciente) {
        StaticFiles.idPaciente = idPaciente;
    }

    public static String getUrlCEP() {
        return urlCEP;
    }

    public static void setUrlCEP(String urlCEP) {
        StaticFiles.urlCEP = urlCEP;
    }

    public static Endereco getEndereco() {
        return endereco;
    }

    public static void setEndereco(Endereco endereco) {
        StaticFiles.endereco = endereco;
    }

    public static String getUrlServer() {
        return urlServer;
    }

    public static void setUrlServer(String urlServer) {
        StaticFiles.urlServer = urlServer;
    }

    public static synchronized Paciente getPaciente() {
        return paciente;
    }

    public static synchronized void setPaciente(Paciente paciente) {

        synchronized (StaticFiles.class) {
            StaticFiles.paciente = paciente;
        }


    }

    public static String getUrlAPIEmail() {
        return urlAPIEmail;
    }

    public static void setUrlAPIEmail(String urlAPIEmail) {
        StaticFiles.urlAPIEmail = urlAPIEmail;
    }

    public static String getAPIEmailKey() {
        return APIEmailKey;
    }

    public static void setAPIEmailKey(String APIEmailKey) {
        StaticFiles.APIEmailKey = APIEmailKey;
    }
}

