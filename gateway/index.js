require('dotenv-safe').config()
const jwt = require('jsonwebtoken')
var http = require('http')
const express = require('express')
const httpProxy = require('express-http-proxy')
const app = express()
var cookieParser = require('cookie-parser')
var bodyParser = require('body-parser')
var logger = require('morgan')
const helmet = require('helmet')
const cors = require('cors');

app.use(cors({
  origin: 'http://localhost:4200'
}));

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }))

// parse application/json
app.use(bodyParser.json())

function verifyJWT(req, res, next) {
  const token = req.headers['authorization']?.replace('Bearer ', '');
  if (!token) {
    return res.status(401).json({
      auth: false,
      message: 'Token não fornecido.',
    });
  }

  jwt.verify(token, process.env.SECRET, function (err, decoded) {
    if (err) {
      return res.status(500).json({ auth: false, message: 'Falha ao autenticar o token.' });
    }

    req.userId = decoded.id;
    next();
  });
}

// proxies
// const exampleProxy = httpProxy(''http://host.docker.internal:5000')

const authServiceProxy = httpProxy('http://host.docker.internal:5000', {
  proxyReqBodyDecorator: function (bodyContent, srcReq) {
    try {
      reqBody = {};
      reqBody.login = bodyContent.username;
      reqBody.senha = bodyContent.password;
      bodyContent = reqBody;
    }
    catch (e) {
      console.log('- ERRO: ' + e);
    }
    return bodyContent;
  },
  proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
    proxyReqOpts.headers['Content-Type'] = 'application/json';
    proxyReqOpts.method = 'POST';
    return proxyReqOpts;
  },
  userResDecorator: function (proxyRes, proxyResData, userReq, userRes) {
    console.log(Buffer.from(proxyResData).toString('utf-8'))
    if (proxyRes.statusCode == 200) {
      var str = Buffer.from(proxyResData).toString('utf-8');
      var objBody = JSON.parse(str);
      const id = objBody.id;
      const token = jwt.sign({ id }, process.env.SECRET, {
        expiresIn: '365d' // expira em 5 min
      });
      userRes.status(200);
      return { auth: true, token: token, data: objBody };
    }
    else {
      userRes.status(401);
      return { message: 'Login inválido!' };
    }
  }
});

// const clientesServiceProxy = httpProxy('http://host.docker.internal:5001');
const clientesServiceProxy = httpProxy('http://localhost:5001');
const clientesPostServiceProxy = httpProxy('http://host.docker.internal:5001', {
  proxyReqBodyDecorator: function (bodyContent, srcReq) {
    try {
      reqBody = {};
      reqBody.nome = bodyContent.nome;
      reqBody.email = bodyContent.email;
      reqBody.cpf = bodyContent.cpf;
      reqBody.endereco = {
        rua: bodyContent.rua,
        numero: bodyContent.numero,
        complemento: bodyContent.complemento,
        cep: bodyContent.cep,
        cidade: bodyContent.cidade,
        estado: bodyContent.estado,
      };
      bodyContent = reqBody;
    }
    catch (e) {
      console.log('- ERRO: ' + e);
    }
    return bodyContent;
  },
  proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
    proxyReqOpts.headers['Content-Type'] = 'application/json';
    proxyReqOpts.method = 'POST';
    return proxyReqOpts;
  }
});

// const reservasServiceProxy = httpProxy('http://host.docker.internal:5002');
const reservasServiceProxy = httpProxy('http://localhost:5003');
const reservasPostServiceProxy = httpProxy('http://host.docker.internal:5002', {
  proxyReqBodyDecorator: function (bodyContent, srcReq) {
    try {
      reqBody = {};
      reqBody.dataHora = bodyContent.dataHora;
      reqBody.estadoReserva = bodyContent.estadoReserva;
      reqBody.codigoVoo = bodyContent.codigoVoo;
      reqBody.idCliente = bodyContent.clienteId;
      reqBody.qntdPassagens = bodyContent.qntdPassagens;
      reqBody.custoTotal = bodyContent.custoTotal;
      reqBody.milhasUsadas= bodyContent.milhasUsadas;
      bodyContent = reqBody;
    }
    catch (e) {
      console.log('- ERRO: ' + e);
    }
    return bodyContent;
  },
  proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
    proxyReqOpts.headers['Content-Type'] = 'application/json';
    proxyReqOpts.method = 'POST';
    return proxyReqOpts;
  }
});

const milhasServiceProxy = httpProxy('http://host.docker.internal:5003');
const milhasPostServiceProxy = httpProxy('http://host.docker.internal:5003', {
  proxyReqBodyDecorator: function (bodyContent, srcReq) {
    try {
      reqBody = {};
      reqBody.idCliente = bodyContent.idCliente;
      reqBody.qntdMilhas = bodyContent.qntdMilhas;
      bodyContent = reqBody;
    }
    catch (e) {
      console.log('- ERRO: ' + e);
    }
    return bodyContent;
  },
  proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
    proxyReqOpts.headers['Content-Type'] = 'application/json';
    proxyReqOpts.method = 'POST';
    return proxyReqOpts;
  }
});

// const funcionariosServiceProxy = httpProxy('http://host.docker.internal:5003');
const funcionariosServiceProxy = httpProxy('http://localhost:5004');
const funcionariosPostServiceProxy = httpProxy('http://localhost:5004', {
  proxyReqBodyDecorator: function (bodyContent, srcReq) {
    console.log(bodyContent)
    try {
      reqBody = {};
      reqBody.nome = bodyContent.nome;
      reqBody.email = bodyContent.email;
      reqBody.cpf = bodyContent.cpf;
      reqBody.telefone = bodyContent.telefone;
      bodyContent = reqBody;
    }
    catch (e) {
      console.log('- ERRO: ' + e);
    }
    return bodyContent;
  },
  proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
    proxyReqOpts.headers['Content-Type'] = 'application/json';
    proxyReqOpts.method = 'POST';
    return proxyReqOpts;
  }
});
const funcionariosPutServiceProxy = httpProxy('http://host.docker.internal:5004', {
  proxyReqBodyDecorator: function (bodyContent, srcReq) {
    try {
      reqBody = {};
      reqBody.nome = bodyContent.nome;
      reqBody.email = bodyContent.email;
      reqBody.cpf = bodyContent.cpf;
      reqBody.telefone = bodyContent.telefone;
      bodyContent = reqBody;
    }
    catch (e) {
      console.log('- ERRO: ' + e);
    }
    return bodyContent;
  },
  proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
    proxyReqOpts.headers['Content-Type'] = 'application/json';
    proxyReqOpts.method = 'PUT';
    return proxyReqOpts;
  }
});

const aeroportoServiceProxy = httpProxy('http://localhost:5002');
// const voosServiceProxy = httpProxy('http://host.docker.internal:5002');
const voosServiceProxy = httpProxy('http://localhost:5002');
const voosPostServiceProxy = httpProxy('http://host.docker.internal:5002', {
  proxyReqBodyDecorator: function (bodyContent, srcReq) {
    try {
      reqBody = {};
      reqBody.cod = bodyContent.codigoVoo;
      reqBody.data = bodyContent.dataHora;
      reqBody.aeroporto_origem = bodyContent.origem;
      reqBody.aeroporto_destino = bodyContent.destino;
      reqBody.valor_passagem = bodyContent.valorPassagem;
      reqBody.qtd_poltronas_total = bodyContent.totalPoltronas;
      bodyContent = reqBody;
    }
    catch (e) {
      console.log('- ERRO: ' + e);
    }
    return bodyContent;
  },
  proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
    proxyReqOpts.headers['Content-Type'] = 'application/json';
    proxyReqOpts.method = 'POST';
    return proxyReqOpts;
  }
});

// Routes

// Auth
app.post('/login', (req, res, next) => {
  authServiceProxy(req, res, next);
});

app.post('/logout', function (req, res) {
  res.json({ auth: false, token: null });
});

// Aeroporto
app.get('/aeroporto', function (req, res, next) {
  aeroportoServiceProxy(req, res, next);
});

// Clientes
app.post('/clientes', function (req, res, next) {
  clientesPostServiceProxy(req, res, next);
});

app.get('/clientes', function (req, res, next) {
  clientesServiceProxy(req, res, next);
});

app.get('/clientes/:id', function (req, res, next) {
  clientesServiceProxy(req, res, next);
});

// Reservas
app.get('/reservas', verifyJWT, function (req, res, next) {
  reservasServiceProxy(req, res, next);
});

app.post('/reservas', verifyJWT, function (req, res, next) {
  reservasServiceProxy(req, res, next);
});

app.get('/reservas/:id', verifyJWT, function (req, res, next) {
  reservasServiceProxy(req, res, next);
});

app.post('/reservas/:id/cancelar', verifyJWT, function (req, res, next) {
  reservasServiceProxy(req, res, next);
});

app.post('/reservas/:id/checkin', verifyJWT, function (req, res, next) {
  reservasServiceProxy(req, res, next);
});

app.post('/reservas/:id/embarque', verifyJWT, function (req, res, next) {
  reservasServiceProxy(req, res, next);
});

// Milhas
app.get('/milhas', verifyJWT, function (req, res, next) {
  milhasServiceProxy(req, res, next);
});

app.post('/milhas', verifyJWT, function (req, res, next) {
  milhasPostServiceProxy(req, res, next);
});


// Funcionarios
app.get('/funcionarios', verifyJWT, function (req, res, next) {
  funcionariosServiceProxy(req, res, next);
});

app.post('/funcionarios', verifyJWT, function (req, res, next) {
  funcionariosPostServiceProxy(req, res, next);
});

app.get('/funcionarios/:id', verifyJWT, function (req, res, next) {
  funcionariosServiceProxy(req, res, next);
});

app.put('/funcionarios/:id', verifyJWT, function (req, res, next) {
  funcionariosPutServiceProxy(req, res, next);
});

app.delete('/funcionarios/:id', verifyJWT, function (req, res, next) {
  funcionariosServiceProxy(req, res, next);
});

// Voos
app.get('/voos', verifyJWT, function (req, res, next) {
  voosServiceProxy(req, res, next);
});

app.post('/voos', verifyJWT, function (req, res, next) {
  console.log(req.body)
  voosPostServiceProxy(req, res, next);
});

app.post('/voos/:id/cancelar', verifyJWT, function (req, res, next) {
  voosServiceProxy(req, res, next);
});

app.post('/voos/:id/realizar', verifyJWT, function (req, res, next) {
  voosServiceProxy(req, res, next);
});

// config
app.use(logger('dev'))
app.use(helmet())
app.use(express.json())
app.use(express.urlencoded({ extended: false }))
app.use(cookieParser())

// server
var server = http.createServer(app)
server.listen(3000)
console.log(`Server listening on port 3000!`)