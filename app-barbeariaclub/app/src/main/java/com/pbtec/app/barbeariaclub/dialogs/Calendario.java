package com.pbtec.app.barbeariaclub.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.interfaces.DataHoraListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadAtualizarHorariosDisponiveis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendario extends DialogFragment {
    private CalendarView calendarView;
    private DataHoraListenner dataHoraListenner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DataHoraListenner){
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
        View view = layoutInflater.inflate(R.layout.content_calendario,null);
        calendarView = (CalendarView)view.findViewById(R.id.calendarView);
        calendarView.setMinDate(new Date().getTime());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                int mes = i1+1;

                //Verificar se o usuário escolheu o domingo
                DateFormat df = new SimpleDateFormat("E, dd MMM yyyy");
                DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                String data = null;
                try {
                    Date d = new Date(df2.parse(i2+"/"+mes+"/"+i).getTime());
                    data = df.format(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(data.charAt(0)=='d') {//se começa com d, então o usuário escolheu domingo
                    Toast.makeText(getContext(), "Desculpe! Não trabalhamos aos domingos. " +
                            "Por favor escolha outro dia para seu atendimento.", Toast.LENGTH_LONG).show();
                }else{
                    dataHoraListenner.data_selecionada(i2+"/"+mes+"/"+i);
                }
            }
        });

        LinearLayout l_layout = (LinearLayout) view.findViewById(R.id.l_layout_cal);
        l_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
        return view;
    }

}
