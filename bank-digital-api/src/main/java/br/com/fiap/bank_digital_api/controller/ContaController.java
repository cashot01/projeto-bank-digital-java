package br.com.fiap.bank_digital_api.controller;

import model.Conta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import DTOs.PixDTO;
import DTOs.SaqueDepositoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ContaController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private List<Conta> repositorio = new ArrayList<>();

    @GetMapping("/conta")
    public ResponseEntity<List<Conta>> obterTodasAsContas() {
        log.info("Recuperando todas as contas");
        return ResponseEntity.ok(repositorio);
    }

    @PostMapping("/conta")
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
        if (conta.getNome_titular() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do titular é obrigatório");
        }
        if (conta.getCpf_titular() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF do titular é obrigatório");
        }
        if (conta.getSaldo_inicial() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo inicial deve ser positivo");
        }
        log.info("Criando conta");
        conta.setId(Math.abs(new java.util.Random().nextLong()));
        repositorio.add(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping("/conta/id/{idConta}")
    public ResponseEntity<Conta> obterContaPorId(@PathVariable Long idConta) {
        log.info("Recuperando conta por ID: " + idConta);
        return ResponseEntity.ok(obterConta(idConta));
    }

    @GetMapping("/conta/cpf/{cpf}")
    public ResponseEntity<Conta> obterContaPorCpf(@PathVariable int cpf) {
        log.info("Recuperando conta por CPF: " + cpf);
        Optional<Conta> conta = repositorio.stream()
                .filter(c -> c.getCpf_titular() == cpf)
                .findFirst();
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/conta/inativar/{idConta}")
    public ResponseEntity<Conta> inativarConta(@PathVariable Long idConta) {
        log.info("Inativando conta");
        Conta conta = obterConta(idConta);
        conta.setAtiva(false);
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/conta/deposito")
    public ResponseEntity<Conta> depositar(@RequestBody SaqueDepositoDTO dadosDeposito) {
        log.info("Solicitação de depósito");
        Conta conta = obterConta(dadosDeposito.idConta());
        conta.setSaldo_inicial(conta.getSaldo_inicial() + dadosDeposito.valor());
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/conta/saque")
    public ResponseEntity<Conta> sacar(@RequestBody SaqueDepositoDTO dadosSaque) {
        log.info("Solicitação de saque");
        Conta conta = obterConta(dadosSaque.idConta());
        if (conta.getSaldo_inicial() >= dadosSaque.valor()) {
            conta.setSaldo_inicial(conta.getSaldo_inicial() - dadosSaque.valor());
            return ResponseEntity.ok(conta);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
    }

    @PutMapping("/conta/pix")
    public ResponseEntity<Conta> transferirPix(@RequestBody PixDTO dadosPix) {
        log.info("Transferindo " + dadosPix.valor() + " da conta " + dadosPix.idConta() + " para a conta " + dadosPix.idContaPix());
        Conta contaOrigem = obterConta(dadosPix.idConta());
        if (contaOrigem.getSaldo_inicial() >= dadosPix.valor()) {
            Conta contaDestino = obterConta(dadosPix.idContaPix());
            contaOrigem.setSaldo_inicial(contaOrigem.getSaldo_inicial() - dadosPix.valor());
            contaDestino.setSaldo_inicial(contaDestino.getSaldo_inicial() + dadosPix.valor());
            return ResponseEntity.ok(contaOrigem);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
    }

    private Conta obterConta(Long id) {
        return repositorio.stream()
                .filter(conta -> conta.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }
}