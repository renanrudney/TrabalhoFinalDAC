export class Funcionario {

  constructor(
    public nome: string,
    public cpf: string,
    public email: string,
    public telefone: string,
    public ativo?: boolean,
    public id?: string
  ){}
}
