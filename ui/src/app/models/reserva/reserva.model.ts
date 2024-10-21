export class Reserva {
  constructor(
    public codigoReserva: string,
    public codigoVoo: string,
    public dataHora: Date,
    public estado: string
  ) {}
}
