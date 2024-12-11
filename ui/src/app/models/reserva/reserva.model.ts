export class Reserva {
  constructor(
    public codigoVoo: string,
    public dataHora: Date,
    public estado: string,
    public clienteId: string,
    public qntdPassagens: number,
    public custoTotal: number,
    public milhasUsadas: number,
    public codigoReserva?: string
  ) {}
}
