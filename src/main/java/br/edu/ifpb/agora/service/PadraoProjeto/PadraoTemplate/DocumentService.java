package br.edu.ifpb.agora.service.PadraoProjeto.PadraoTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifpb.agora.model.Documento;
import br.edu.ifpb.agora.model.EntidadesSalvarDocumento;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.repository.DocumentoRepository;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import jakarta.transaction.Transactional;

import org.springframework.util.StringUtils;


@Service
public abstract class DocumentService {

    @Autowired
    protected DocumentoRepository documentoRepository;

    
    @Transactional
    public void save(Long id, List<MultipartFile> files) {
        EntidadesSalvarDocumento entidade = pesquisarEntidade(id);
        files.forEach((file) -> {
            String nameFile = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Documento document = new Documento(nameFile, file.getBytes());
                document = salvarDocumento(entidade, document);
                document.setUrl(this.buildUrl(entidade.getId(), document.getId()));
            } catch (IOException e) {

                e.printStackTrace();
            }
        });



        
    }

    public abstract EntidadesSalvarDocumento pesquisarEntidade(Long id);

    public abstract Documento salvarDocumento(EntidadesSalvarDocumento entidade, Documento documento);

    public Documento getDocumento(Long id) {
        return documentoRepository.findById(id).get();
    }

    protected abstract String buildUrl(Long id, Long idDocumento);
}