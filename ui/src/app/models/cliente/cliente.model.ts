export class Cliente {
  constructor(
    public cpf: string,
    public nome: string,
    public email: string,
    public rua: string,
    public numero: number,
    public complemento: string | null,
    public cep: string,
    public cidade: string,
    public estado: string,
    public milhas: number,
    public id?: number
  ) {}
}
