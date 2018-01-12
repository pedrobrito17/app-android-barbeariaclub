package com.pbtec.app.barbeariaclub.dialogs;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.interfaces.DataHoraListenner;

import java.util.ArrayList;
import java.util.List;

public class ListaHoraDisponivel extends DialogFragment {
    private ListView listaview_hora;
    private String item_selecionado;
    private List<String> lista_horarios;
    private final String MY_PREF = "PreferênciasApp";
    private DataHoraListenner dataHoraListenner;

    @SuppressLint("ValidFragment")
    public ListaHoraDisponivel (List<String> horarios_disponiveis){
        this.lista_horarios = horarios_disponiveis;
    }

    public ListaHoraDisponivel(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataHoraListenner){
            dataHoraListenner = (DataHoraListenner) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " deve implementar DataHoraListenner");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.content_lista_hora_disponivel,null);

        listaview_hora = (ListView)view.findViewById(R.id.listView_hora);
        listaview_hora.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataHoraListenner.hora_selecionada(lista_horarios.get(i));
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,lista_horarios);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        listaview_hora.setAdapter(arrayAdapter);
    }

    public interface ListenerHora{
        public String hora_selecionada(String hora_selecionada);
    }


}
