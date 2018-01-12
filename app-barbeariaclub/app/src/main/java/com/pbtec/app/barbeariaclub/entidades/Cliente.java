package com.pbtec.app.barbeariaclub.entidades;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by root on 12/08/16.
 */
public class Cliente implements KvmSerializable, Serializable{
    private String nome;
    private String email;
    private int ddd;
    private String telefone;
    private String senha;

    public Cliente(String nome, String email, int ddd, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.ddd = ddd;
        this.telefone = telefone;
        this.senha = senha;
    }

    public Cliente() {

    }

    public String getPrimeiroNome(){
        String primeiro_nome = String.valueOf(nome.charAt(0));
        int i=1;
        while(i<nome.length()){
            if(nome.charAt(i)==' '){
                break;
            }else{
                primeiro_nome = primeiro_nome + nome.charAt(i);
                i++;
            }
        }
        return primeiro_nome;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return nome;
            case 1:
                return email;
            case 2:
                return ddd;
            case 3:
                return telefone;
            case 4:
                return senha;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        switch (arg0) {
            case 0:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "nome";
                break;
            case 1:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "email";
                break;
            case 2:
                arg2.type = PropertyInfo.INTEGER_CLASS;
                arg2.name = "ddd";
                break;
            case 3:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "telefone";
                break;
            case 4:
                arg2.type = PropertyInfo.STRING_CLASS;
                arg2.name = "senha";
                break;
            default:
                break;
        }
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        switch (arg0) {
            case 0:
                nome = arg1.toString();
                break;
            case 1:
                email= arg1.toString();
                break;
            case 2:
                ddd = Integer.parseInt(arg1.toString());
                break;
            case 3:
                telefone = arg1.toString();
                break;
            case 4:
                senha = arg1.toString();
                break;
            default:
                break;
        }
    }

}