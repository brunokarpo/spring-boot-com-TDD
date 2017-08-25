package com.example.demo.repository;

import com.example.demo.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Bruno Nogueira de Oliveira
 * @date 23/08/17.
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByCpf(String cpf);

    @Query("SELECT bean FROM Pessoa bean WHERE 1=1")
    Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);
}
