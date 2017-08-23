package com.example.demo.servico;

import com.example.demo.modelo.Pessoa;
import com.example.demo.servico.exception.UnicidadeCpfException;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 23/08/17.
 */
public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException;
}
