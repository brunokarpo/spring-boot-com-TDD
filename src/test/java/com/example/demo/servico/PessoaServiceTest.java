package com.example.demo.servico;

import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.servico.exception.UnicidadeCpfException;
import com.example.demo.servico.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 23/08/17.
 */
@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    private static final String NOME = "Bruno Nogueira";
    private static final String CPF = "12345678912";

    @MockBean
    private PessoaRepository pessoaRepository;

    private PessoaService sut;

    private Pessoa pessoa;

    @Before
    public void setUp() throws Exception {
        sut = new PessoaServiceImpl(pessoaRepository);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
    }

    @Test
    public void deve_salvar_pessoa_no_repositorio() throws Exception {
        sut.salvar(pessoa);

        verify(pessoaRepository).save(pessoa);
    }

    @Test(expected = UnicidadeCpfException.class)
    public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

        sut.salvar(pessoa);
    }
}
