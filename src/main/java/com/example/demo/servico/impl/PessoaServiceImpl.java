package com.example.demo.servico.impl;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.PessoaService;
import com.example.demo.servico.exception.UnicidadeCpfException;

import java.util.Optional;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 23/08/17.
 */
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException {
        Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());

        if( optional.isPresent() ) {
            throw new UnicidadeCpfException();
        }

        return pessoaRepository.save(pessoa);
    }
}
