package br.edu.ifpb.agora.service.PadraoProjeto.ChainOfResponsibility;

import br.edu.ifpb.agora.model.Professor;

public interface Handler {
    public void setNextHandler(BaseHandler handler);
    public void handle();
    public BaseHandler getNextHandler();

}
