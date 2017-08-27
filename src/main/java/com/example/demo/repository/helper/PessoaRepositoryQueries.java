package com.example.demo.repository.helper;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.filtro.PessoaFiltro;

import java.util.List;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 27/08/17.
 */
public interface PessoaRepositoryQueries {

    List<Pessoa> filtrar(PessoaFiltro filtro);
}
