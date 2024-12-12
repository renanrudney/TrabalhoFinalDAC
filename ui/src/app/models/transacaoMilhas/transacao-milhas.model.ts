export class TransacaoMilhas {
  constructor(
    public idCliente: string,
    public dataHora: Date,
    public qtdMilhas: number,
    public entrada: boolean,
    public descricao: string,
    public codigoReserva?: string
  ) {}
}
