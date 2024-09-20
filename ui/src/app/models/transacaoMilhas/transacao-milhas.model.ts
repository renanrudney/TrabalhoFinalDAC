export class TransacaoMilhas {
  constructor(
    public clienteCpf: string,
    public dataHora: Date,
    public quantidadeMilhas: number,
    public tipo: 'entrada' | 'saida',
    public descricao: string
  ) {}
}
