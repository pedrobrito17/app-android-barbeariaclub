/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteDAO;

import connectionbd.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro-brito
 */
public class ClienteDAO {
    private Connection conn;
    
    public String insertCliente(Cliente cliente){
        String insertSQL = "INSERT INTO Cliente(nome,email,ddd,telefone,senha) "
                + "VALUES(?,?,?,?,?)";
        
        String email_repetido = "SELECT * FROM Cliente WHERE email=?";
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                PreparedStatement prd = conn.prepareStatement(email_repetido);
                prd.setString(1, cliente.getEmail());
                ResultSet result = prd.executeQuery();
                if(result.next()){
                    return "E-mail já cadastrado";
                }else{              
                    prd = conn.prepareStatement(insertSQL);
                    prd.setString(1, cliente.getNome());
                    prd.setString(2, cliente.getEmail());
                    prd.setInt(3, cliente.getDdd());
                    prd.setString(4, cliente.getTelefone());
                    prd.setString(5, cliente.getSenha());
                    prd.execute();        
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return "Cadastro realizado com sucesso";
        }else{
            return "Sistema indisponível";
        }
    }
    
    public Cliente selectCliente(String email, String senha){
        String selectSQL = "select * from Cliente where email=? and senha=?";
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                PreparedStatement prd = conn.prepareStatement(selectSQL);            
                prd.setString(1, email);
                prd.setString(2, senha);
                ResultSet result = prd.executeQuery();
                if(result.next()){
                    Cliente cliente = new Cliente();
                    cliente.setNome(result.getString("nome"));
                    cliente.setEmail(result.getString("email"));
                    cliente.setDdd(result.getInt("ddd"));
                    cliente.setTelefone(result.getString("telefone"));
                    cliente.setSenha(result.getString("senha"));
                    return cliente;
                } else{
                    return selectFuncionario(email, senha);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally{
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);  
                }
                
            }
        }
        return null;
    }
    
    public Cliente selectFuncionario(String email, String senha){
        String selectSQL = "select * from Funcionario where email_funcionario=? and senha=?";
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try {
                PreparedStatement prd = conn.prepareStatement(selectSQL);            
                prd.setString(1, email);
                prd.setString(2, senha);
                ResultSet result = prd.executeQuery();
                if(result.next()){
                    Cliente funcionario = new Cliente();
                    funcionario.setNome(result.getString("nome_funcionario"));
                    funcionario.setEmail(result.getString("email_funcionario"));
                    funcionario.setDdd(99);
                    funcionario.setTelefone("nada");
                    funcionario.setSenha(result.getString("senha"));
                    return funcionario;
                } else{
                    return null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally{
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);  
                }
                
            }
        }
        return null;
    }
    
    public String updateSenhaCliente(String email, String senha){
        String sql = "UPDATE Cliente set senha=? WHERE email=?";
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try{
                PreparedStatement prd = conn.prepareStatement(sql);
                prd.setString(1,senha);
                prd.setString(2,email);
                prd.executeUpdate();
                return "Senha alterada com sucesso";
            }catch(SQLException e){
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
            }finally{
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        return "Sistema indisponível";
    }
    
    public String updateCliente(Cliente cliente, String email_antigo){
        String sql = "UPDATE Cliente set nome=?,email=?,ddd=?,telefone=? WHERE email=?";
        String email_repetido = "SELECT * FROM Cliente WHERE email=?";
        
        conn = new ConnectionBD().conectarBD();
        if(conn!=null){
            try{
                PreparedStatement prd;
                if(!cliente.getEmail().equals(email_antigo)){
                    prd = conn.prepareStatement(email_repetido);
                    prd.setString(1, cliente.getEmail());
                    ResultSet result = prd.executeQuery();
                    if(result.next()){
                        return "E-mail já cadastrado";
                    }
                }
                prd = conn.prepareStatement(sql);
                prd.setString(1,cliente.getNome());
                prd.setString(2,cliente.getEmail());
                prd.setInt(3,cliente.getDdd());
                prd.setString(4,cliente.getTelefone());
                prd.setString(5,email_antigo);
                prd.executeUpdate();
                return "Cadastro atualizado";                
            }catch(SQLException e){
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }finally{
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        return "Sistema indisponível";
    }
 
}
