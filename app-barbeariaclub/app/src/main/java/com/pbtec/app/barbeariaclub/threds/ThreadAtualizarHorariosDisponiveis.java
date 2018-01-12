package com.pbtec.app.barbeariaclub.threds;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.comunicadorwebservice.AtendimentoWS;
import com.pbtec.app.barbeariaclub.dialogs.ListaHoraDisponivel;
import com.pbtec.app.barbeariaclub.interfaces.DataHoraListenner;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22/08/16.
 */
public class ThreadAtualizarHorariosDisponiveis extends AsyncTask<String,Void,List<String>>{
    private ProgressDialog load;
    private Context context;
    private DataHoraListenner mListener;

    public ThreadAtualizarHorariosDisponiveis(Context context) {
        this.context = context;
        if (context instanceof DataHoraListenner) {
            mListener = (DataHoraListenner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        load = ProgressDialog.show(context,"","Carregando os horários disponíveis...");
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        return new AtendimentoWS().selectHorasAgendadas(strings[0],strings[1]);
    }

    @Override
    protected void onPostExecute(List<String> list) {
        load.dismiss();
        mListener.inserirHorariosDisponiveis(list);

    }
}
