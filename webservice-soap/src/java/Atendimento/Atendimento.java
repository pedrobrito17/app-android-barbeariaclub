/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Atendimento;

import java.sql.Date;

/**
 *
 * @author pedro-brito
 */
public class Atendimento {
    private String email_cliente;
    private String email_funcionario;
    private String desc_servico;
    private String data_atendimento;
    private String hora_atendimento;

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }

    public String getEmail_funcionario() {
        return email_funcionario;
    }

    public void setEmail_funcionario(String email_funcionario) {
        this.email_funcionario = email_funcionario;
    }

    public String getDesc_servico() {
        return desc_servico;
    }

    public void setDesc_servico(String desc_servico) {
        this.desc_servico = desc_servico;
    }

    public String getData_atendimento() {
        return data_atendimento;
    }

    public void setData_atendimento(String data_atendimento) {
        this.data_atendimento = data_atendimento;
    }

    public String getHora_atendimento() {
        return hora_atendimento;
    }

    public void setHora_atendimento(String hora_atendimento) {
        this.hora_atendimento = hora_atendimento;
    }
    
    
}
