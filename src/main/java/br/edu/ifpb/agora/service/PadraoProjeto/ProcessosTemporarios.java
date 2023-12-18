package br.edu.ifpb.agora.service.PadraoProjeto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.agora.model.Processo;

public class ProcessosTemporarios {

    private Map<String, List<Processo>> processosTemp = new HashMap<String, List<Processo>>();

    private static ProcessosTemporarios instance = null;

    private ProcessosTemporarios() {
    }

    public static ProcessosTemporarios getInstance() {

        if (instance == null) {
            instance = new ProcessosTemporarios();
        }

        return instance;


    }
    
    public void addProcesso(String username, Processo processo) {
        if(processosTemp.containsKey(username)) {
            processosTemp.get(username).add(processo);

        }
        else {
            List<Processo> processos = new ArrayList<Processo>();
            processos.add(processo);
            processosTemp.put(username, processos);

        }
    }

    public List<Processo> deletaProcessos(String username) {
        return processosTemp.remove(username);
    }

    public List<Processo> getProcessos(String username) {
        return processosTemp.get(username);
    }
}
