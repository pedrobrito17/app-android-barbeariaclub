/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Atendimento;

import java.util.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro-brito
 */
public class Formatador {
    
    public java.sql.Date getDateSQL(String data){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.sql.Date dataSQL = new java.sql.Date(format.parse(data).getTime());
            return dataSQL;
        } catch (ParseException ex) {
            Logger.getLogger(Formatador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Time getTime(String horario){
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
        try {
            Date data = formatador.parse(horario);
            return new Time(data.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Formatador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String stringDate(java.sql.Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }
}
