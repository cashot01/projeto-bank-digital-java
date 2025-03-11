package br.com.fiap.bank_digital_api.controller;

import br.com.fiap.bank_digital_api.model.Conta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContaController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private List<Conta> repository = new ArrayList<>();
    @GetMapping("/contas")
    public List<Conta> index(){
        log.info("Buscando todas as contas");
        return repository;
    }
    @PostMapping("/contas")
    public ResponseEntity<Conta> create(@RequestBody Conta conta){
        log.info("Cadastando conta " + conta.getNome_titular());
        repository.add(conta);
        return ResponseEntity.status(201).body(conta);
    }

}
