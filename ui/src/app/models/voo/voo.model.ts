import { Aeroporto } from "../aeroporto/aeroporto.model";

export class Voo {
  constructor(
    public codigoVoo: string,
    public dataHora: Date,
    public origem: Aeroporto,    // Aeroporto de origem
    public destino: Aeroporto,   // Aeroporto de destino
    public valorPassagem: number, // Valor em reais
    public totalPoltronas: number,
    public poltronasOcupadas: number
  ) {}
}
