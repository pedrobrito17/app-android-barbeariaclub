package com.pbtec.app.barbeariaclub.interfaces;

import com.pbtec.app.barbeariaclub.entidades.Atendimento;

import java.util.List;

/**
 * Created by root on 23/08/16.
 */
public interface AgendaListenner {

    public void popularListView(List<Atendimento> lista);
}
