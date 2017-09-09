package com.example.demo.servico.impl;

import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.Telefone;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.PessoaService;
import com.example.demo.servico.exception.TelefoneNaoEncontradoException;
import com.example.demo.servico.exception.UnicidadeCpfException;
import com.example.demo.servico.exception.UnicidadeTelefoneException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 23/08/17.
 */
@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {
        Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());

        if( optional.isPresent() ) {
            throw new UnicidadeCpfException("Já existe pessoa cadastrada com o CPF '"+pessoa.getCpf()+"'");
        }

        final String ddd = pessoa.getTelefones().get(0).getDdd();
        final String numero = pessoa.getTelefones().get(0).getNumero();
        optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(ddd, numero);

        if( optional.isPresent() ) {
            throw new UnicidadeTelefoneException();
        }

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
        final Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
        return optional.orElseThrow(() ->
                new TelefoneNaoEncontradoException("Não existe pessoa com o telefone (" + telefone.getDdd() + ")" + telefone.getNumero()));
    }
}
