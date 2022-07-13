package io.github.aguinaldoneto.vendas.rest.controller;

import io.github.aguinaldoneto.vendas.exception.PedidoNaoEncontradoException;
import io.github.aguinaldoneto.vendas.exception.RegraNegocioException;
import io.github.aguinaldoneto.vendas.rest.ApiErrors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handlerRegraNegocioException(RegraNegocioException ex) {
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors PedidoNaoEncontradoException(PedidoNaoEncontradoException ex) {
        String messagemErro = ex.getMessage();
        return new ApiErrors(messagemErro);
    }
}
