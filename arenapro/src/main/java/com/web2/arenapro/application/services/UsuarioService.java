package com.web2.arenapro.application.services;

import com.web2.arenapro.application.services.exceptions.DatabaseException;
import com.web2.arenapro.application.services.exceptions.ResourceNotFoundException;
import com.web2.arenapro.domain.entities.Usuario;
import com.web2.arenapro.domain.repositories.UsuarioRepository;
import com.web2.arenapro.domain.dto.UsuarioDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado")
        );
        return new UsuarioDTO(usuario);
    }

    @Transactional()
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {

        Usuario entity = new Usuario();

        copyDtoToEntity(usuarioDTO, entity);

        entity = usuarioRepository.save(entity);
        return new UsuarioDTO(entity);

    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {

        try {
            Usuario entity = usuarioRepository.getReferenceById(id);

            copyDtoToEntity(usuarioDTO, entity);

            entity = usuarioRepository.save(entity);

            return new UsuarioDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id não encontrado");
        }

        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro no banco");
        }

    }

    private void copyDtoToEntity(UsuarioDTO usuarioDTO, Usuario entity) {

        entity.setNome(usuarioDTO.getNome());
        entity.setCpf(usuarioDTO.getCpf());
        entity.setEmail(usuarioDTO.getEmail());
        entity.setSenha(usuarioDTO.getSenha());
        entity.setTelefone(usuarioDTO.getTelefone());

    }

}
