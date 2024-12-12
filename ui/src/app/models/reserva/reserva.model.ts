export class Reserva {
  constructor(
    public codVoo: string,
    public data_hora: Date,
    public estado: string,
    public id_cliente: string,
    public qntdPassagens: number,
    public valorGasto: number,
    public milhasGasto: number,
    public cod?: string
  ) {}
}
