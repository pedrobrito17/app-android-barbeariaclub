/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.com;

import Atendimento.Atendimento;
import Atendimento.AtendimentoDAO;
import clienteDAO.Cliente;
import clienteDAO.ClienteDAO;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author pedro-brito
 */
@WebService(serviceName = "NovoWebService")
public class WS_BarbeariaClub {

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "insertCliente")
    public String insertBD(@WebParam(name = "cliente") Cliente cliente) {
        return new ClienteDAO().insertCliente(cliente);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "verificarCliente")
    public Cliente verificarUsuario(@WebParam(name = "email") String email, @WebParam(name = "senha") String senha) {
        return new ClienteDAO().selectCliente(email, senha);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "getAtendimentos")
    public List<Atendimento> getAtendimentos(@WebParam(name = "id") int id, @WebParam(name = "email_cliente") String email_cliente, @WebParam(name = "email_funcionario") String email_funcionario, @WebParam(name = "data_atendimento") String data_atendimento) {
        List<Atendimento> lista = new AtendimentoDAO().selectAtendimento(id, email_cliente, email_funcionario, data_atendimento);
        return lista;
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "setAtendimento")
    public Boolean setAtendimento(@WebParam(name = "atendimento") Atendimento atendimento) {
        boolean a = new AtendimentoDAO().insertAtendimento(atendimento);
        return a;
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "getAtendimentosAgendados")
    public List<Atendimento> getAtendimentosAgendados() {
        List<Atendimento> lista = new AtendimentoDAO().selectAtendimentosAgendados();
        return lista;
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "atualizarSenha")
    public String atualizarSenha(@WebParam(name = "email") String email, @WebParam(name = "nova_senha") String nova_senha) {
        return new ClienteDAO().updateSenhaCliente(email, nova_senha);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "atualizarCliente")
    public String atualizarCliente(@WebParam(name = "cliente") Cliente cliente, @WebParam(name = "email_antigo") String email_antigo) {
        return new ClienteDAO().updateCliente(cliente, email_antigo);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "selecionarHorasAgendadas")
    public List<String> selecionarHorasAgendadas(@WebParam(name = "email_funcionario") String email_funcionario, @WebParam(name = "data_atendimento") String data_atendimento) {
        return new AtendimentoDAO().selectHoraDisponível(email_funcionario, data_atendimento);
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "deleteAtendimento")
    public Boolean deleteAtendimento(@WebParam(name = "data") String data, @WebParam(name = "hora") String hora) {
        return new AtendimentoDAO().deleteAtendimento(data, hora);
    }


    




}
