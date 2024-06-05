# Agendamento-de-transfer-ncias-finaceiras
Sistema de agendamento de transferências financeiras.
Front/Back

Decisões arquiteturais:
    Arquitetura Monolítica
        Uma aplicação monolítica tende a ser mais simples de desenvolver, implantar e manter, pois todo o código está em um único projeto.
        Gerenciar uma única aplicação é geralmente mais fácil do que coordenar várias partes em um ambiente de microsserviços.
        A comunicação entre os componentes de uma aplicação monolítica geralmente é mais rápida do que em um ambiente de microsserviços, já que não há necessidade de comunicação via rede.
        Escalar uma aplicação monolítica geralmente envolve aumentar os recursos da máquina em que ela é executada, o que pode ser limitado.
        Padrão MVC (Model-repositories-Controller-service) Utilize o padrão MVC para separar claramente a lógica de negócios (Model), a apresentação (repositories) e o controle (Controller). Isso ajuda na manutenção e na escalabilidade do código.


versões:
    NPM      - 10.4.0
    Node     - 20.11.0
    Vue.Js   - 5.0.8
    Java/JDK - 11.0
