package br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifpb.agora.model.Documento;
import br.edu.ifpb.agora.model.EntidadesSalvarDocumento;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.repository.DocumentoRepository;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import jakarta.transaction.Transactional;

@Service
public class DocumentServiceParecerProcesso extends DocumentService {

    @Autowired
    ProcessoRepository processoRepository;

    @Autowired
    protected DocumentoRepository documentoRepository;

    @Override
    public EntidadesSalvarDocumento pesquisarEntidade(Long id) {
        // TODO Auto-generated method stub
        return processoRepository.findById(id).get();



    }

    @Override
    @Transactional
    public Documento salvarDocumento(EntidadesSalvarDocumento entidade, Documento documento) {
        // TODO Auto-generated method stub
        Processo processo = (Processo) entidade;
        processo.setParecer(documento);
        processoRepository.save(processo);
        return processo.getParecer();



    }

    @Override
    protected String buildUrl(Long id, Long idDocumento) {
        // TODO Auto-generated method stub

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/processo/")
                .path(id.toString())
                .path("/parecer")
                .path("/documento/")
                .path(idDocumento.toString())
                .toUriString();
        return fileDownloadUri;

    }
}
