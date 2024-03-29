package br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifpb.agora.model.Documento;
import br.edu.ifpb.agora.model.EntidadesSalvarDocumento;
import br.edu.ifpb.agora.model.Reuniao;
import br.edu.ifpb.agora.repository.DocumentoRepository;
import br.edu.ifpb.agora.repository.ReuniaoRepository;
import jakarta.transaction.Transactional;

@Service
public class DocumentServiceAtaReuniao extends DocumentService {

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    protected DocumentoRepository documentoRepository;

    @Override
    public EntidadesSalvarDocumento pesquisarEntidade(Long id) {
        // TODO Auto-generated method stub
        return reuniaoRepository.findById(id).get();
    }


    @Transactional
    public Documento salvarDocumento(EntidadesSalvarDocumento entidade, Documento documento) {
        Reuniao reuniao = (Reuniao) entidade;
        Reuniao reuniaoBD = reuniaoRepository.findById(reuniao.getId()).get();
        reuniaoBD.setAta(documento);
        reuniaoRepository.save(reuniaoBD);
        return reuniaoBD.getAta();
        

        
    }

    @Override
    protected String buildUrl(Long id, Long idDocumento) {
        // TODO Auto-generated method stub
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/coordenador/")
                .path("/sessao/")
                .path(String.valueOf(id))
                .path("/ata")
                .path("/documento/")
                .path(String.valueOf(idDocumento))
                .toUriString();
        return fileDownloadUri;
    }




    
    
}
