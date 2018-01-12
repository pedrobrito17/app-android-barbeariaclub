package com.pbtec.app.barbeariaclub.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;


public class HomeLogged extends Fragment {
    private View view;
    private Button agendar;
    private FragmentListenner mlistener;

    @Override
    public void onAttach(Context context) { //1º etapa do ciclo de vida
        super.onAttach(context);
        if (context instanceof FragmentListenner) { //verifica se o contexto instaciou a interface
            mlistener = (FragmentListenner) context; //se sim, a interface recebe o contexto (cast)
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_logged, container, false);

        WebView web = (WebView)view.findViewById(R.id.web_view2);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "A Barbearia Club vem para inovar e resgatar a nostalgia de fazer barba, cabelo e bigode " +
                "com toalha quente e navalha. Alinhada com produtos para homens, em um ambiente " +
                "masculino e vintage, ela nos tras momentos de um passado com um olhar para o futuro.";
        text+= "</p></body></html>";
        web.loadData(text, "text/html", "utf-8");


        agendar = (Button)view.findViewById(R.id.agendar_horario);
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mlistener!=null){ //se a variavel recebeu o contexto, então...
                    mlistener.chamarFragment(1); //chama o método que a interface fez a classe escrever
                }
            }
        });
        return view;
    }


}
