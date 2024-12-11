export class TransacaoMilhas {
  constructor(
    public clienteId: string,
    public dataHora: Date,
    public quantidadeMilhas: number,
    public tipo: 'entrada' | 'saida',
    public descricao: string,
    public codigoReserva?: string
  ) {}
}
