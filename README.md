# Agendamento-de-transfer-ncias-finaceiras
Sistema de agendamento de transferências financeiras.

versões:
    NPM             - 10.4.0 / BootstrapVue - 10.4.0 / router - 10.4.0
    Node            - 20.11.0
    Vue.Js          - 5.0.8
    Java/JDK        - 17.0
    springframework - 3.3.0
    jjwt            - 0.9.1
    crypto
    Database: h2 e mysql
---BACKEND-------------------------------------------------------

  Decisões arquiteturais:
      Arquitetura Monolítica
          Uma aplicação monolítica tende a ser mais simples de desenvolver, implantar e manter, pois todo o código está em um único projeto.
          Gerenciar uma única aplicação é geralmente mais fácil do que coordenar várias partes em um ambiente de microsserviços.
          A comunicação entre os componentes de uma aplicação monolítica geralmente é mais rápida do que em um ambiente de microsserviços, já que não há necessidade de comunicação via rede.
          Escalar uma aplicação monolítica geralmente envolve aumentar os recursos da máquina em que ela é executada, o que pode ser limitado.
          Padrão MVC (Model-repositories-Controller-service) Utilizei o padrão MVC para separar claramente a lógica de negócios (Model), a apresentação (repositories), serviço (Service), e o controle (Controller). Isso ajuda na manutenção e na escalabilidade do código.

  Endpoints:
    Banco de dados
    http://localhost:9000/h2

    POST
        Criando Registro  
            http://localhost:9000/api/auth/signup
            {
                "email": "usuario@gmail.com", //não pode registrar com um email já registrado
                "password": "000",
                "cliente": {
                    "nome": "usuarioUser",
                    "cpf": "123456789",
                    "endereco": "Rua Exemplo, 123",
                    "contas": [
                        {
                            "agencia": "0001", // campo automatico no Vue.js
                            "banco": "BlueStar Bank", // campo automatico no Vue.js
                            "numero": "56789", // campo automatico no Vue.js
                            "saldo": 1000.00 // campo automatico no Vue.js
                        }
                    ]
                }
            }
        Login
            http://localhost:9000/api/auth/signin
            {
                "email": "usuario@gmail.com",
                "password": "000"
            }
        Agendar transferencias
            { //crie no minimo dois registros em api/auth/signup
                "contaOrigemId": 1,
                "contaDestinoId": 2,
                "valor": 1000.00,
                "dataAgendada": "2024-07-01"
            }

    GET
        http://localhost:9000/api/contas
        http://localhost:9000/api/contas/{id}

        http://localhost:9000/api/clientes
        http://localhost:9000/api/clientes/{id}

        http://localhost:9000/api/agendamentos
        http://localhost:9000/api/agendamentos/{id}

---FRONTEND-------------------------------------------------------

- 100% responsivo
