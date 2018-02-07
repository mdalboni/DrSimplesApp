package overload.drmed.overload.drmed.models;

import java.util.ArrayList;

/**
 * Created by Dalbo on 25/04/2017.
 */

public class ListaHorarios extends SuccessLog{

    ArrayList<HorariosDisponiveis> HorariosDisponiveis;

    public ArrayList<ListaHorarios.HorariosDisponiveis> getHorariosDisponiveis() {
        return HorariosDisponiveis;
    }

    public void setHorariosDisponiveis(ArrayList<ListaHorarios.HorariosDisponiveis> horariosDisponiveis) {
        HorariosDisponiveis = horariosDisponiveis;
    }

    public class HorariosDisponiveis{
        String dtMarcada;
        boolean disponivel;

        public String getDtMarcada() {
            return dtMarcada;
        }

        public boolean isDisponivel() {
            return disponivel;
        }

        public void setDisponivel(boolean disponivel) {
            this.disponivel = disponivel;
        }

        public void setDtMarcada(String dtMarcada) {
            this.dtMarcada = dtMarcada;
        }
    }
}
