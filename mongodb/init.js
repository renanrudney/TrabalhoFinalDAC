db = db.getSiblingDB('auth');  // Cria ou acessa o banco de dados 'auth'

db.createCollection('usuario');  // Cria a coleção 'usuario'

// (Opcional) Insere um exemplo na coleção 'usuario'
db.usuario.insert({
  login: "allbano@gmail.com",
  senha: "dac-exemplo",
  tipo: "0"
});
