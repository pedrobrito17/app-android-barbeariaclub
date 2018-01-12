package com.pbtec.app.barbeariaclub.arrayadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.threds.ThreadDeleteAtendimento;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by root on 13/09/16.
 */
public class AdapterRecycleView extends RecyclerView.Adapter{
    private List<Atendimento> lista;
    private TextView data, hora;
    private Atendimento atendimento;
    private final String MY_PREF = "PreferênciasApp";
    private Context context;

    public AdapterRecycleView(Context context, List<Atendimento> lista) {
        this.lista = lista;
        this.context = context;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView data, hora, servico, barbeiro;
        public ImageButton lixeira;

        public CustomViewHolder(View view) {
            super(view);
            data = (TextView)view.findViewById(R.id.minha_ag_data);
            hora = (TextView)view.findViewById(R.id.minha_ag_hora);
            servico = (TextView)view.findViewById(R.id.minha_ag_servico);
            barbeiro = (TextView)view.findViewById(R.id.minha_ag_barbeiro);
            lixeira = (ImageButton)view.findViewById(R.id.lixeira);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.content_array_adapter_atendimento,null);

        RecyclerView.ViewHolder vh = new CustomViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CustomViewHolder customViewHolder = (CustomViewHolder) holder;

        Atendimento atendimento = lista.get(position);

        if(atendimento.getEmail_funcionario().equals("henrique123@gmail.com") ||
                atendimento.getEmail_funcionario().equals("rafa_mene@gmail.com") ||
                        atendimento.getEmail_funcionario().equals("fernando_raposo@gmail.com")){

            customViewHolder.lixeira.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Atendimento atend = (Atendimento) customViewHolder.lixeira.getTag();
                    ThreadDeleteAtendimento thread = new ThreadDeleteAtendimento(context);
                    String email_cliente = context.getSharedPreferences(MY_PREF, 0).getString("email_cliente", "nulo");
                    thread.execute(email_cliente, atend.getData_atendimento(), atend.getHora_atendimento());
                }
            });
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat df2 = new SimpleDateFormat("E, dd MMM yyyy");
        try {
            customViewHolder.data.setText(df2.format(df.parse(atendimento.getData_atendimento())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        customViewHolder.hora.setText(atendimento.getHora_atendimento());
        customViewHolder.servico.setText(atendimento.getDesc_servico());
        if(atendimento.getEmail_funcionario().equals("henrique123@gmail.com")){customViewHolder.barbeiro.setText("Henrique");}
        else if(atendimento.getEmail_funcionario().equals("rafa_mene@gmail.com")){customViewHolder.barbeiro.setText("Rafael");}
        else if(atendimento.getEmail_funcionario().equals("fernando_raposo@gmail.com")){customViewHolder.barbeiro.setText("Fernando");}
        else {
            customViewHolder.barbeiro.setText(atendimento.getEmail_cliente());

        }
        customViewHolder.lixeira.setTag(atendimento); //add um objeto a cada tag de cada botão da lista
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
}
