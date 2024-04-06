package com.jcs.BateriaStore.services;

import com.jcs.BateriaStore.dtos.EnderecoDto;
import com.jcs.BateriaStore.entities.Endereco;
import com.jcs.BateriaStore.entities.User;
import com.jcs.BateriaStore.repositories.EnderecoRepository;
import com.jcs.BateriaStore.repositories.UserRepository;
import com.jcs.BateriaStore.services.exceptions.ExceptionBD;
import com.jcs.BateriaStore.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<EnderecoDto> findAll() {
        List<Endereco> list = repository.findAll();
        return list.stream().map(x -> new EnderecoDto(x)).collect(Collectors.toList());
    }

    @Transactional
    public List<EnderecoDto> getByUserId(Long userId) {
        User user = userRepository.getReferenceById(userId);
        List<Endereco> list = repository.findbyUserId(user);
        return list.stream().map(x -> new EnderecoDto(x)).collect(Collectors.toList());
    }

    @Transactional
    public EnderecoDto findById(Long id) {
        Optional<Endereco> obj = repository.findById(id);
        Endereco entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new EnderecoDto(entity);
    }

    // Criar Endereço
    @Transactional
    public EnderecoDto insert(EnderecoDto dto) {
        Endereco entity = new Endereco();

        entity.setCep(dto.getCep());
        entity.setBairro(dto.getBairro());
        entity.setComplemento(dto.getComplemento());
        entity.setLogradouro(dto.getLogradouro());
        entity.setLocalidade(dto.getLocalidade());

        User user = new User();
        user.setId(dto.getUserId());
        entity.setUser(user);

        entity = repository.save(entity);
        return new EnderecoDto(entity);

    }

    // Atualizar Endereço
    @Transactional
    public EnderecoDto update(Long id, EnderecoDto dto) {
        try {
            Endereco entity = repository.getReferenceById(id);
            entity.setCep(dto.getCep());
            entity.setBairro(dto.getBairro());
            entity.setComplemento(dto.getComplemento());
            entity.setLogradouro(dto.getLogradouro());
            entity.setLocalidade(dto.getLocalidade());
            entity = repository.save(entity);
            return new EnderecoDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado " + id);
        }
    }

    // Excluir Endereço (id)
    public void delete (Long id){
            try {
                repository.deleteById(id);
            } catch (EmptyResultDataAccessException e) {
                throw new ResourceNotFoundException("Id not found " + id);
            } catch (DataIntegrityViolationException e) {
                throw new ExceptionBD("ID não permitido para exclusão");
            }
        }
    }

//Add Transactional annotation