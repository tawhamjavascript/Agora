package br.edu.ifpb.agora.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.agora.model.Authority;
import br.edu.ifpb.agora.model.User;
import br.edu.ifpb.agora.repository.UserRepository;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String userHome(Principal principal) {
        User user = userRepository.findById(principal.getName()).get();
        List<Authority> authorities = user.getAuthorities();

        System.out.println(authorities.get(0).getAuthority());
        String result;
        if (authorities.size() > 1) {
            result = "/coordenador/home";
            
        }
        else if (authorities.get(0).getAuthority().equals("ROLE_ALUNO")) {
            result = "/aluno/processo";
        }
        else if (authorities.get(0).getAuthority().equals("ROLE_PROFESSOR")){
            result = "/professor/home";
        }
        else {
            result = "/admin/home";
        }

        return result;
    }
    
}
