package com.pbtec.app.barbeariaclub.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.threds.ThreadAgenda;
import com.pbtec.app.barbeariaclub.threds.ThreadDeleteAtendimento;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ArrayAdapterAtendimento extends ArrayAdapter<Atendimento> {
    private Context context;
    private List<Atendimento> lista;
    private final String MY_PREF = "PreferênciasApp";
    private TextView data, hora;
    private int id_linha_selecionada;
    private Atendimento atendimento;

    public ArrayAdapterAtendimento(Context context, int resource, List<Atendimento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.lista = objects;
    }

    public ArrayAdapterAtendimento(){
        super(null,0);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.content_array_adapter_atendimento,null);

        TextView barbeiro = (TextView)view.findViewById(R.id.minha_ag_barbeiro);
        data = (TextView)view.findViewById(R.id.minha_ag_data);
        hora = (TextView)view.findViewById(R.id.minha_ag_hora);
        TextView servico = (TextView)view.findViewById(R.id.minha_ag_servico);
        final ImageButton lixeira = (ImageButton)view.findViewById(R.id.lixeira);

        atendimento = lista.get(position);

        lixeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Atendimento atend = (Atendimento) lixeira.getTag();
                ThreadDeleteAtendimento thread = new ThreadDeleteAtendimento(getContext());
                String email_cliente = getContext().getSharedPreferences(MY_PREF,0).getString("email_cliente","nulo");
                thread.execute(email_cliente, atend.getData_atendimento(), atend.getHora_atendimento());
            }
        });

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat df2 = new SimpleDateFormat("E, dd MMM yyyy");
        try {
            data.setText(df2.format(df.parse(atendimento.getData_atendimento())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        hora.setText(atendimento.getHora_atendimento());
        servico.setText(atendimento.getDesc_servico());
        if(atendimento.getEmail_funcionario().equals("henrique123@gmail.com")){barbeiro.setText("Henrique");}
        else if(atendimento.getEmail_funcionario().equals("rafa_mene@gmail.com")){barbeiro.setText("Rafael");}
        else {barbeiro.setText("Fernando");}
        lixeira.setTag(atendimento); //add um objeto a cada tag de cada botão da lista

        return view;
    }
}
