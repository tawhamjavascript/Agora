package br.edu.ifpb.agora.service.PadraoProjeto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.model.Reuniao;

public class DB4O {

    private Map<String, Reuniao> objetos = new HashMap<String, Reuniao>();

    private static DB4O instance = null;

    private DB4O() {

    }

    public void addReuniao(String user, Reuniao reuniao) {
        this.objetos.put(user, reuniao);

    }

    public static DB4O getInstance() {

        if (instance == null) {
            instance = new DB4O();
        }

        return instance;
    }

    public void cadastrarReuniao(String username, Reuniao reuniao) {
        if(!existeReuniao(username)) {
            this.objetos.put(username, reuniao);

        } 
    }
    
    public void addProcesso(String username, Processo processo) {
        Reuniao reuniao = objetos.get(username);
        if (reuniao.getProcessos() == null) {
            reuniao.setProcessos(new ArrayList<>());
        }
        reuniao.addProcesso(processo);
    
    }

    public Reuniao deletar(String username) {
        return objetos.remove(username);
    }

    public Reuniao getReuniao(String username) {
        return objetos.get(username);
    }

    public boolean existeReuniao(String username) {
        return this.objetos.containsKey(username);
    }

    public List<Processo> getReunioesProcesso(String username) {
        Reuniao reuniao = this.objetos.get(username);
        if(reuniao == null) {
            return new ArrayList<>();
        }
        return reuniao.getProcessos();
    }
}
