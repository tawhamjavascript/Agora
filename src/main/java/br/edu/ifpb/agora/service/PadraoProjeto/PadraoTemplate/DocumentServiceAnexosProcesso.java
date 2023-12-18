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
public class DocumentServiceAnexosProcesso extends DocumentService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

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
        processo.addAnexos(documento);
        processoRepository.save(processo);
        return processo.getUltimoAnexo();
        
    
    }

    @Override
    protected String buildUrl(Long id, Long idDocumento) {
        System.out.println(id.toString() + idDocumento.toString());
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/aluno")
                .path("/processo/")
                .path(String.valueOf(id))
                .path("/anexo/")
                .path(String.valueOf(idDocumento))
                .toUriString();

        
        System.out.println(fileDownloadUri);
        return fileDownloadUri;









        
    }    
}
