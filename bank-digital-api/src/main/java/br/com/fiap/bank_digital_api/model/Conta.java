package br.com.fiap.bank_digital_api.model;

import java.util.Date;
import java.util.Random;

public class Conta {
    private Long id;
    private int num_agencia;
    private String nome_titular;
    private int cpf_titular;
    private Date data_abertura;
    private double saldo_inicial;
    private boolean ativa;
    private String tipo;

    public Conta(Long id, int num_agencia, String nome_titular, int cpf_titular, Date data_abertura, double saldo_inicial, boolean ativa, String tipo) {
        this.id = Math.abs(new Random().nextLong());
        this.num_agencia = num_agencia;
        this.nome_titular = nome_titular;
        this.cpf_titular = cpf_titular;
        this.data_abertura = data_abertura;
        this.saldo_inicial = saldo_inicial;
        this.ativa = ativa;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public int getNum_agencia() {
        return num_agencia;
    }

    public String getNome_titular() {
        return nome_titular;
    }

    public int getCpf_titular() {
        return cpf_titular;
    }

    public Date getData_abertura() {
        return data_abertura;
    }

    public double getSaldo_inicial() {
        return saldo_inicial;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public String getTipo() {
        return tipo;
    }
    public void setId(Long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", num_agencia=" + num_agencia +
                ", nome_titular='" + nome_titular + '\'' +
                ", cpf_titular=" + cpf_titular +
                ", data_abertura=" + data_abertura +
                ", saldo_inicial=" + saldo_inicial +
                ", ativa=" + ativa +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
