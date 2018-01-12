package com.pbtec.app.barbeariaclub.threds;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.AtendimentoWS;
import com.pbtec.app.barbeariaclub.entidades.Atendimento;
import com.pbtec.app.barbeariaclub.interfaces.AgendaListenner;

import java.util.List;

/**
 * Created by root on 23/08/16.
 */
public class ThreadAgenda extends AsyncTask<String,Void,List<Atendimento>>{
    private ProgressDialog load;
    private Context context;
    private AgendaListenner mListenner;

    public ThreadAgenda(Context context){
        this.context = context;
        if(context instanceof AgendaListenner){
            mListenner = (AgendaListenner) context;
        }else{
            throw new RuntimeException("Activity precisa implementar Interface AgendaListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        load = ProgressDialog.show(context,"","Carregando...");
    }

    @Override
    protected List<Atendimento> doInBackground(String... strings) {
        Log.i("AtendimentoWS",strings[0]);
        return new AtendimentoWS().selectAtendimento(1,strings[0],"nulo","nulo");
    }

    @Override
    protected void onPostExecute(List<Atendimento> atendimentos) {
        if(atendimentos==null){
            load.dismiss();
            Toast.makeText(context,"Desculpe! Não foi possível acessar sua agenda",Toast.LENGTH_LONG).show();
        }else{
            load.dismiss();
            mListenner.popularListView(atendimentos);
        }
    }
}
