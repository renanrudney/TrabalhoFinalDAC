export class Reserva {
  constructor(
    public codigoVoo: string,
    public dataHora: Date,
    public estado: string,
    public clienteId: number,
    public qntdPassagens: number,
    public custoTotal: number,
    public milhasUsadas: number,
    public codigoReserva?: string
  ) {}
}
