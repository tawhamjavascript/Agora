package br.edu.ifpb.agora.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifpb.agora.model.Documento;
import br.edu.ifpb.agora.model.Processo;
import br.edu.ifpb.agora.repository.DocumentoRepository;
import br.edu.ifpb.agora.repository.ProcessoRepository;
import org.springframework.util.StringUtils;


@Service
public class DocumentService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    public void save(Long idProcesso, List<MultipartFile> files) {
      
        Processo processo = processoRepository.findById(idProcesso).get();
        files.forEach((file) -> {
            String nameFile = StringUtils.cleanPath(file.getOriginalFilename());
            try {

                Documento document = new Documento(nameFile, file.getBytes());
                processo.addAnexos(document);
                document.setProcesso(processo);
                processoRepository.save(processo);
                documentoRepository.save(document);
                document.setUrl(this.buildUrl(processo.getId(), document.getId()));
            
            } catch (IOException e) {

                e.printStackTrace();
            }
        });

        processoRepository.save(processo);
        
    }

    public Documento getDocumento(Long id) {
        return documentoRepository.findById(id).get();
    }


    private String buildUrl(Long idProcesso, Long idDocumento) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/aluno")
                .path("/processo/")
                .path(String.valueOf(idProcesso))
                .path("/documentos/")
                .path(String.valueOf(idDocumento))
                .toUriString();
        return fileDownloadUri;
    }
}