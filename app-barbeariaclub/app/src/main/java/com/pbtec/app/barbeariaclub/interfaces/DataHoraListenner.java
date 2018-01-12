package com.pbtec.app.barbeariaclub.interfaces;

import java.util.List;

/**
 * Created by root on 21/08/16.
 */
public interface DataHoraListenner {
    public void hora_selecionada(String hora);
    public void data_selecionada(String data);
    public void inserirHorariosDisponiveis(List<String> horarios_indisponiveis);
}
