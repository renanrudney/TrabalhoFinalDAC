export class Voo {
  constructor(
    public cod: string,
    public data: Date,
    public aeroporto_origem: string,    // Aeroporto de origem
    public aeroporto_destino: string,   // Aeroporto de destino
    public valor_passagem: number, // Valor em reais
    public qtd_poltronas_total: number,
    public qtd_poltronas_ocupadas: number,
    public estado?: string
  ) {}
}
