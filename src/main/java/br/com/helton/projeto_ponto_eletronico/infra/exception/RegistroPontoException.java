package br.com.helton.projeto_ponto_eletronico.infra.exception;

public class RegistroPontoException extends RuntimeException{
    public RegistroPontoException(String mensagem) {
        super(mensagem);
    }
}
