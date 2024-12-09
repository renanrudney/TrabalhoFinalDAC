package br.ufpr.dac.ms_orchestrator.rabbitmq.config;

public class RabbitMQConstants {
  public static final String FILA_CLIENTE_CRIADO = "CLIENTE_CRIADO";
  public static final String FILA_CRIAR_USUARIO_CLIENTE = "CRIAR_USUARIO_CLIENTE";

  public static final String FILA_SOLICITAR_USUARIO_CLIENTE = "SOLICITAR_USUARIO_CLIENTE";
  public static final String FILA_USUARIO_CLIENTE_CRIADO = "USUARIO_CLIENTE_CRIADO";
  public static final String FILA_RETORNAR_USUARIO_CLIENTE = "RETORNAR_CLIENTE_USUARIO";


  public static final String FILA_CRIAR_CLIENTE = "CRIAR_CLIENTE";
  
  public static final String FILA_VOO_REALIZADO = "VOO_REALIZADO";
  public static final String FILA_RESERVAS_REALIZADO = "RESERVAS_REALIZADO_VOO";
  public static final String FILA_VOO_CANCELADO = "VOO_CANCELADO";
  public static final String FILA_RESERVAS_CANCELADO = "RESERVAS_CANCELADO_VOO";

  public static final String[] TODAS_FILAS = {
    FILA_CLIENTE_CRIADO, FILA_SOLICITAR_USUARIO_CLIENTE, FILA_CRIAR_USUARIO_CLIENTE, FILA_USUARIO_CLIENTE_CRIADO, FILA_RETORNAR_USUARIO_CLIENTE, FILA_CLIENTE_CRIADO, FILA_CRIAR_CLIENTE, FILA_VOO_REALIZADO, FILA_VOO_CANCELADO, FILA_RESERVAS_REALIZADO, FILA_RESERVAS_CANCELADO
  };
}
