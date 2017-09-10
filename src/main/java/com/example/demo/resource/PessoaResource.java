package com.example.demo.resource;

import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.Telefone;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.repository.filtro.PessoaFiltro;
import com.example.demo.servico.PessoaService;
import com.example.demo.servico.exception.TelefoneNaoEncontradoException;
import com.example.demo.servico.exception.UnicidadeCpfException;
import com.example.demo.servico.exception.UnicidadeTelefoneException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 09/09/17.
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository repository;

    @GetMapping("/{ddd}/{numero}")
    public ResponseEntity<Pessoa> buscarPorDddENumeroDoTelefone(@PathVariable("ddd") String ddd,
                                                                @PathVariable("numero") String numero) throws TelefoneNaoEncontradoException {
        final Telefone telefone = new Telefone();
        telefone.setDdd(ddd);
        telefone.setNumero(numero);

        final Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);

        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pessoa> salvarNova(@RequestBody Pessoa pessoa, HttpServletResponse response) throws UnicidadeCpfException, UnicidadeTelefoneException {
        final Pessoa pessoaSalva = pessoaService.salvar(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{ddd}/{numero}")
                .buildAndExpand(pessoa.getTelefones().get(0).getDdd(), pessoa.getTelefones().get(0).getNumero()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return new ResponseEntity<>(pessoaSalva, HttpStatus.CREATED);
    }

    @PostMapping("/filtrar")
    public ResponseEntity<List<Pessoa>> filtrar(@RequestBody PessoaFiltro filtro) {
        final List<Pessoa> pessoas = repository.filtrar(filtro);
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @ExceptionHandler({UnicidadeCpfException.class})
    public ResponseEntity<Erro> handleUnicidadeCpfException(UnicidadeCpfException e) {
        return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TelefoneNaoEncontradoException.class})
    public ResponseEntity<Erro> handleTelefoneNaoEncontradoException(TelefoneNaoEncontradoException e) {
        return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    class Erro {
        private final String erro;

        public Erro(String erro) {
            this.erro = erro;
        }

        public String getErro() {
            return erro;
        }
    }

}
