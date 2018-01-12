package com.pbtec.app.barbeariaclub.interfaces;

/**
 * Created by root on 04/08/16.
 */
public interface FragmentListenner { //Será a intermediária para que a activity e os fragments conversem e ouçam

    public void chamarFragment(int i);
    public boolean verificarInternet();
    public void verificarRetorno(String s);
}
