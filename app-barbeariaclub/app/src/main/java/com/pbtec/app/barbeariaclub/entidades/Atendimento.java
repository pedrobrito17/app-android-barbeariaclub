package com.pbtec.app.barbeariaclub.entidades;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by root on 12/08/16.
 */
public class Atendimento implements KvmSerializable {
    private String email_cliente;
    private String email_funcionario;
    private String desc_servico;
    private String data_atendimento;
    private String hora_atendimento;

    public Atendimento() {

    }

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

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return email_cliente;
            case 1:
                return email_funcionario;
            case 2:
                return desc_servico;
            case 3:
                return data_atendimento;
            case 4:
                return hora_atendimento;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                email_cliente = o.toString();
                break;
            case 1:
                email_funcionario = o.toString();
                break;
            case 2:
                desc_servico = o.toString();
                break;
            case 3:
                data_atendimento = o.toString();
                break;
            case 4:
                hora_atendimento = o.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hshtbl, PropertyInfo pi) {
        switch (i) {
            case 0:
                pi.type = PropertyInfo.STRING_CLASS;
                pi.name = "email_cliente";
                break;
            case 1:
                pi.type = PropertyInfo.STRING_CLASS;
                pi.name = "email_funcionario";
                break;
            case 2:
                pi.type = PropertyInfo.STRING_CLASS;
                pi.name = "desc_servico";
                break;
            case 3:
                pi.type = PropertyInfo.STRING_CLASS;
                pi.name = "data_atendimento";
                break;
            case 4:
                pi.type = PropertyInfo.STRING_CLASS;
                pi.name = "hora_atendimento";
                break;
            default:
                break;
        }
    }
}
