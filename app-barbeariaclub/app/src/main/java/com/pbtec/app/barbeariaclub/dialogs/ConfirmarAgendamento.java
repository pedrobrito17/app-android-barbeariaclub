package com.pbtec.app.barbeariaclub.dialogs;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.threds.ThreadInserirAtendimento;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConfirmarAgendamento extends DialogFragment {
    private TextView barbeiro,data,hora,servico;
    private String s_barbeiro, s_data, s_hora, s_servico, email_barbeiro;
    private final String MY_PREF = "PreferênciasApp";

    @SuppressLint("ValidFragment")
    public ConfirmarAgendamento(String email_barbeiro, String barbeiro, String data, String hora, String servico){
        super();
        this.email_barbeiro = email_barbeiro;
        this.s_barbeiro = barbeiro;
        this.s_data = data;
        this.s_hora = hora;
        this.s_servico = servico;
    }

    public ConfirmarAgendamento(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.content_confirmar_agendamento,null);

        barbeiro = (TextView)view.findViewById(R.id.barbeiro);
        barbeiro.setText("Barbeiro: "+s_barbeiro);
        data = (TextView)view.findViewById(R.id.data);
        data.setText("Data: "+s_data);
        hora = (TextView)view.findViewById(R.id.hora);
        hora.setText("Horário: "+s_hora);
        servico = (TextView)view.findViewById(R.id.servico);
        servico.setText("Serviço: " + s_servico);

        Button cancelar = (Button)view.findViewById(R.id.cancelar_ag);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Button confirmar = (Button)view.findViewById(R.id.confirmar_ag);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Atendimento atendimento = new Atendimento();
                atendimento.setEmail_cliente(getContext().getSharedPreferences(MY_PREF,0).
                        getString("email_cliente","000"));
                atendimento.setEmail_funcionario(email_barbeiro);
                //necessário devido a formatação do webservice
                String hora = ""+s_hora.charAt(0)+s_hora.charAt(1)+":"+s_hora.charAt(5)+s_hora.charAt(6)+":00";
                atendimento.setHora_atendimento(hora);
                atendimento.setDesc_servico(s_servico);

                DateFormat df = new SimpleDateFormat("E, dd MMM yyyy");
                DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                String data=null;
                try {
                    data = df2.format(df.parse(s_data));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                atendimento.setData_atendimento(data);

                dismiss();
                ThreadInserirAtendimento thread = new ThreadInserirAtendimento(getContext());
                thread.execute(atendimento);
            }
        });
        return view;
    }
}
