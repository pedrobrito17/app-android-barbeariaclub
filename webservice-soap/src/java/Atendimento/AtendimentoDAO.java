/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Atendimento;

import connectionbd.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro-brito
 */
public class AtendimentoDAO {
    private Connection conn;
    private String comandoSQL;
    private PreparedStatement prd;
    private ResultSet result;
    private List<Atendimento> lista;
    
    public boolean insertAtendimento(Atendimento atendimento){
        comandoSQL = "INSERT INTO Atendimento(email_cliente,email_func,desc_serv,data_atendimento,hora_atendimento) VALUES (?,?,?,?,?)";

        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                prd = conn.prepareStatement(comandoSQL);
                prd.setString(1, atendimento.getEmail_cliente());
                prd.setString(2, atendimento.getEmail_funcionario());
                prd.setString(3, atendimento.getDesc_servico());
                prd.setDate(4, new Formatador().getDateSQL(atendimento.getData_atendimento()));
                prd.setTime(5, new Formatador().getTime(atendimento.getHora_atendimento()));
                prd.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally{
                try {
                    conn.close();
                    prd.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }            
        }
        return false;
    }
 
    
    public List<Atendimento> selectAtendimento(int id, String email_cliente, String email_funcionario,
            String data_atendimento){
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
                Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
                if(id==1){
                    comandoSQL = "SELECT * FROM Atendimento WHERE email_cliente=?";
                    prd = conn.prepareStatement(comandoSQL);
                    prd.setString(1, email_cliente);
                    result = prd.executeQuery();
                }else if(id==2){
                    comandoSQL = "select * from Atendimento where email_func=? and data_atendimento=current_date() order by hora_atendimento asc";
                    prd = conn.prepareStatement(comandoSQL);
                    prd.setString(1, email_funcionario);
                    result = prd.executeQuery();
                }else{
                    comandoSQL = "SELECT * FROM Atendimento WHERE data_atendimento=?";
                    prd = conn.prepareStatement(comandoSQL);
                    prd.setDate(1, new Formatador().getDateSQL(data_atendimento));
                    result = prd.executeQuery();
                }
                
                lista = new ArrayList<Atendimento>();
                Atendimento atendimento;
                while(result.next()){
                    atendimento = new Atendimento();
                    atendimento.setEmail_cliente(result.getString("email_cliente"));
                    Time time = new Time(result.getTime("hora_atendimento").getTime());  
                    atendimento.setHora_atendimento(time.toString());
                    atendimento.setEmail_funcionario(result.getString("email_func"));
                    atendimento.setDesc_servico(result.getString("desc_serv"));
                    atendimento.setData_atendimento(new Formatador().stringDate(result.getDate("data_atendimento")));
                    if(id==2){
                        String email_c = result.getString("email_cliente");
                        comandoSQL = "SELECT nome FROM Cliente WHERE email=?";
                        PreparedStatement prd2 = conn.prepareStatement(comandoSQL);
                        prd2.setString(1, email_c);
                        ResultSet result2 = prd2.executeQuery();
                        if(result2.next()){
                            atendimento.setEmail_cliente(result2.getString("nome"));
                            atendimento.setEmail_funcionario("null");
                        }					
                    }
                    lista.add(atendimento);  
                }
                conn.commit();
                if(!lista.isEmpty()){
                    return lista;
                }else{
                    return null;
                }
                
            }catch(SQLException ex){
                Logger.getLogger("Erro", prd.toString());
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally{
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                    prd.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
    public List<Atendimento> selectAtendimentosAgendados(){
        comandoSQL ="SELECT * FROM Atendimento WHERE data_atendimento>=CURDATE() "
                + "ORDER BY data_atendimento ASC";
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                prd = conn.prepareStatement(comandoSQL);
                result = prd.executeQuery();
                lista = new ArrayList();
                Atendimento atendimento;
                while(result.next()){
                    atendimento = new Atendimento();
                    atendimento.setEmail_cliente(result.getString("email_cliente"));
                    atendimento.setEmail_funcionario(result.getString("email_func"));
                    atendimento.setDesc_servico(result.getString("desc_serv"));
                    atendimento.setData_atendimento(new Formatador().stringDate(result.getDate("data_atendimento")));
                    Time time = new Time(result.getTime("hora_atendimento").getTime());                  
                    atendimento.setHora_atendimento(time.toString());
                    lista.add(atendimento);
                }
                if(!lista.isEmpty()){return lista;}else{return null;}
                
            } catch (SQLException ex) {
                Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                try {
                    conn.close();
                    prd.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        return null;
    }
    
    public List<String> selectHoraDisponível(String email_funcionario, String data_atendimento){
        comandoSQL = "SELECT hora_atendimento FROM Atendimento WHERE data_atendimento=? "
                + "AND email_func=?";
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                prd = conn.prepareStatement(comandoSQL);
                prd.setDate(1, new Formatador().getDateSQL(data_atendimento));
                prd.setString(2, email_funcionario);
                ResultSet result = prd.executeQuery();
                List<String> hora_indisponivel = new ArrayList<String>();
                while(result.next()){
                    try {
                        hora_indisponivel.add(new SimpleDateFormat("HH : mm").
                                format(result.getTime("hora_atendimento")));
                    } catch (SQLException ex) {
                        Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(hora_indisponivel.isEmpty()){return null;}
                else{return hora_indisponivel;}
            } catch (SQLException ex) {
                Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally{
                try {
                    conn.close();
                    prd.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
    public boolean deleteAtendimento(String data, String hora){
        comandoSQL = "DELETE FROM Atendimento WHERE data_atendimento=? AND hora_atendimento=?";
    
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                prd = conn.prepareStatement(comandoSQL);
                prd.setDate(1, new Formatador().getDateSQL(data));
                prd.setTime(2, new Formatador().getTime(hora));
                prd.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally{
                try {
                    conn.close();
                    prd.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AtendimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }            
        }
        return false;
    }
    
}
