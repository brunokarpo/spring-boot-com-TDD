package com.example.demo.resource;

import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.Telefone;
import com.example.demo.servico.PessoaService;
import com.example.demo.servico.exception.TelefoneNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 09/09/17.
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/{ddd}/{numero}")
    public ResponseEntity<Pessoa> buscarPorDddENumeroDoTelefone(@PathVariable("ddd") String ddd,
                                                                @PathVariable("numero") String numero) throws TelefoneNaoEncontradoException {
        final Telefone telefone = new Telefone();
        telefone.setDdd(ddd);
        telefone.setNumero(numero);

        final Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);

        return new ResponseEntity<>(pessoa, HttpStatus.OK);
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
