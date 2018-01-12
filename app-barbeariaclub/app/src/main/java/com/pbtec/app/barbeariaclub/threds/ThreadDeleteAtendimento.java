package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.AtendimentoWS;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.interfaces.AgendaListenner;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;

import java.util.List;

/**
 * Created by root on 05/09/16.
 */
public class ThreadDeleteAtendimento extends AsyncTask<String,Void,Boolean> {
    private ProgressDialog load;
    private Context context;
    private List<Atendimento> lista;
    private AgendaListenner mListenner;

    public ThreadDeleteAtendimento(Context context){
        this.context = context;
        if(context instanceof AgendaListenner){
            mListenner = (AgendaListenner) context;
        }else{
            throw new RuntimeException("Activity precisa implementar Interface AgendaListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        load = ProgressDialog.show(context,"","Aguarde...");
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean b = new AtendimentoWS().deletarAtendimento(strings[1],strings[2]);
        lista = new AtendimentoWS().selectAtendimento(1,strings[0],"nulo","nulo");
        return b;
    }

    @Override
    protected void onPostExecute(Boolean a) {
        if (a==true){
            load.dismiss();
            mListenner.popularListView(lista);
        }else{
            load.dismiss();
            Toast.makeText(context, "Desculpe! Não foi possível deletar o atendimento",Toast.LENGTH_LONG).show();
        }
    }
}
