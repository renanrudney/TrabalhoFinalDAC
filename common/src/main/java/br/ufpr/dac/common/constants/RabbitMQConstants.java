package br.ufpr.dac.common.constants;

public class RabbitMQConstants {
  public static final String FILA_CRIAR_USUARIO_CLIENTE = "CRIAR_USUARIO_CLIENTE";
  public static final String FILA_USUARIO_CLIENTE_CRIADO = "USUARIO_CLIENTE_CRIADO";
  public static final String FILA_CRIAR_CLIENTE = "CRIAR_CLIENTE";

  public static final String[] TODAS_FILAS = {
    FILA_CRIAR_USUARIO_CLIENTE, FILA_USUARIO_CLIENTE_CRIADO, FILA_CRIAR_CLIENTE
  };
}
