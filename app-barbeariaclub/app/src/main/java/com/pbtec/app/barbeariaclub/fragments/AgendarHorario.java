package com.pbtec.app.barbeariaclub.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.dialogs.Calendario;
import com.pbtec.app.barbeariaclub.dialogs.ConfirmarAgendamento;
import com.pbtec.app.barbeariaclub.dialogs.ListaHoraDisponivel;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadAtualizarHorariosDisponiveis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AgendarHorario extends Fragment {
    private DialogFragment dialogFragment;
    private final String MY_PREF = "PreferênciasApp";
    private String email_barbeiro=null;
    private String servico_selecionado;
    private String nome_barbeiro=null;
    private FragmentListenner fragmentListenner;

    public AgendarHorario(Context context){
        if(context instanceof FragmentListenner){
            fragmentListenner = (FragmentListenner) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_agendar_horario, container, false);

        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case (R.id.radioButton):
                        email_barbeiro = "henrique123@gmail.com";
                        nome_barbeiro = "Henrique";
                        break;
                    case(R.id.radioButton2):
                        email_barbeiro = "rafa_mene@gmail.com";
                        nome_barbeiro = "Rafael";
                        break;
                    case(R.id.radioButton3):
                        email_barbeiro = "fernando_raposo@gmail.com";
                        nome_barbeiro = "Fernando";
                        break;
                    default:
                        break;
                }
            }
        });

        final Button selecionar_data = (Button)view.findViewById(R.id.selecionar_data);
        DateFormat df = new SimpleDateFormat("E, dd MMM yyyy");
        selecionar_data.setText(df.format(new Date().getTime()));
        selecionar_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new Calendario();
                dialogFragment.show(getActivity().getFragmentManager(),"calendario");
            }
        });

        final Button selecionar_hora = (Button)view.findViewById(R.id.selecionar_hora);
        selecionar_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragmentListenner.verificarInternet()){
                    if (email_barbeiro == null) {
                        Toast.makeText(getContext(), "Escolha seu barbeiro", Toast.LENGTH_LONG).show();
                    } else {
                        DateFormat df = new SimpleDateFormat("E, dd MMM yyyy");
                        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                        String data = null;
                        try {
                            data = df2.format(df.parse(selecionar_data.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ThreadAtualizarHorariosDisponiveis thread = new ThreadAtualizarHorariosDisponiveis(getContext());
                        thread.execute(email_barbeiro, data);
                    }
                }
            }
        });

        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        String [] servicos = {"Corte de cabelo com tesoura","Corte de cabelo com máquina", "Barba"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,servicos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    servico_selecionado = "Corte de cabelo com tesoura";
                }else if(i==1){
                    servico_selecionado = "Corte de cabelo com máquina";
                }else if(i==2){
                    servico_selecionado = "Barba";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button conf_agendamento = (Button)view.findViewById(R.id.confirmar_agendamento);
        conf_agendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragmentListenner.verificarInternet()) {
                    if (nome_barbeiro == null) {
                        Toast.makeText(getContext(), "Selecione um barbeiro", Toast.LENGTH_LONG).show();
                    } else {
                        DialogFragment dialog = new ConfirmarAgendamento(email_barbeiro, nome_barbeiro, selecionar_data.getText().toString(),
                                selecionar_hora.getText().toString(), servico_selecionado);
                        dialog.show(getActivity().getFragmentManager(), "confirmar_agendamento");
                    }
                }
            }
        });
        return view;
    }




}
