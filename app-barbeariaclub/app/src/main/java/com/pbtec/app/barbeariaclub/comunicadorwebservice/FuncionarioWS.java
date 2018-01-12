package com.pbtec.app.barbeariaclub.comunicadorwebservice;

import android.util.Log;

import com.pbtec.app.barbeariaclub.entidades.Cliente;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FuncionarioWS {

    private final String NAMESPACE = "http://com.ws/";
    private final String METODO_1 = "verificarFuncionario";
    private final String URL = "http://node128040-server-pb.jelasticlw.com.br:8080/webservicebc/NovoWebService?wsdl";
    //"http://192.168.0.5:8080/WebApp/NovoWebService?wsdl";

    public Cliente verificarFuncionario(String email, String senha){
        SoapObject soap = new SoapObject(NAMESPACE,METODO_1);
        soap.addProperty("email", email);
        soap.addProperty("senha", senha);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call(NAMESPACE+METODO_1, envelope);
            Log.i("KSOAP","recebeu o objeto");
            SoapObject resposta = (SoapObject) envelope.getResponse();
            Cliente cliente = new Cliente();
            cliente.setNome(resposta.getProperty("nome").toString());
            cliente.setEmail(resposta.getPropertyAsString("email"));
            cliente.setDdd(Integer.parseInt(resposta.getProperty("ddd").toString()));
            cliente.setTelefone(resposta.getProperty("telefone").toString());
            cliente.setSenha(resposta.getProperty("senha").toString());
            if(!cliente.getNome().isEmpty()){
                return cliente;
            }
        } catch (IOException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlPullParserException ex) {
            Logger.getLogger(ClienteWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
