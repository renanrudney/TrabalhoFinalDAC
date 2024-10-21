export class TransacaoMilhas {
  constructor(
    public cliente: string,
    public dataHora: Date,
    public quantidadeMilhas: number,
    public tipo: 'entrada' | 'saida',
    public descricao: string
  ) {}
}
