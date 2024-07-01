<template>
    <div class="transferencia-container">
        <!-- Botão para alternar entre modos -->
        <button @click="toggleTransferenciaMode" class="button-style">
            {{ transferirAgoraVisivel ? 'Agendadar Transferência' : 'Transferir Agora ' }}
        </button>

        <h1 v-if="!transferirAgoraVisivel">Transferência Agendada</h1>

        <!-- Formulário de Transferência Agendada -->
        <form v-if="!transferirAgoraVisivel" @submit.prevent="agendarTransferencia" class="transferencia-form">
            <div class="form-group">
                <label for="contaDestinoId">Conta de Destino:</label>
                <input type="number" id="contaDestinoId" v-model="transferencia.contaDestinoId" @focus="buscarContas"
                    required>
                <ul v-if="mostrarListaContas">
                    <li v-for="conta in contas" :key="conta.id" @click="selecionarConta(conta)">{{ conta.banco }} -
                        Agência: {{ conta.agencia }} - Conta: {{ conta.numero }}</li>
                </ul>
            </div>
            <div class="form-group">
                <label for="valor">Valor:</label>
                <input type="number" step="0.01" id="valor" v-model="transferencia.valor" required>
            </div>
            <div class="form-group">
                <label for="dataAgendada">Data Agendada:</label>
                <input type="date" id="dataAgendada" v-model="transferencia.dataAgendada" required>
                <input type="time" id="horaAgendada" v-model="horaAgendada" required>
            </div>
            <button class="button-style" type="submit">Agendar Transferência</button>
        </form>

        <!-- Formulário de Transferência Imediata -->
        <div v-if="transferirAgoraVisivel" class="transferir-agora">
            <h1>Transferência Imediata</h1>
            <form @submit.prevent="transferirAgora" class="transferencia-form">
                <div class="form-group">
                    <label for="contaDestinoIdImediato">Conta de Destino:</label>
                    <input type="number" id="contaDestinoIdImediato" v-model="transferenciaImediata.contaDestinoId"
                        @focus="buscarContas" required>
                    <ul v-if="mostrarListaContas">
                        <li v-for="conta in contas" :key="conta.id" @click="selecionarContaImediata(conta)">{{
                            conta.banco }} - Agência: {{ conta.agencia }} - Conta: {{ conta.numero }} - Saldo: {{
                                conta.saldo }}</li>
                    </ul>
                </div>
                <div class="form-group">
                    <label for="valorImediato">Valor:</label>
                    <input type="number" step="0.01" id="valorImediato" v-model="transferenciaImediata.valor" required>
                </div>
                <button class="button-style" type="submit">Transferir Agora</button>
            </form>
        </div>

        <!-- Feedback para o usuário -->
        <div v-if="mensagem" class="feedback">
            <p>{{ mensagem }}</p>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'TransferenciaComponent',
    data() {
        return {
            transferencia: {
                contaOrigemId: '',
                contaDestinoId: '',
                valor: '',
                dataAgendada: ''
            },
            transferenciaImediata: {
                contaDestinoId: '',
                valor: '',
                dataAgendada: new Date().toISOString().split('T')[0]
            },
            horaAgendada: '',
            mensagem: '',
            contas: [],
            mostrarListaContas: false,
            transferirAgoraVisivel: false // Inicialmente mostra o formulário de transferência agendada
        };
    },
    created() {
        this.transferencia.contaOrigemId = this.$route.params.contaOrigemId;
    },
    methods: {
        agendarTransferencia() {
            const valorTransferencia = parseFloat(this.transferencia.valor);
            const dataHoraAgendada = `${this.transferencia.dataAgendada}T${this.horaAgendada}`;
            const dataHoraAgendadaObj = new Date(dataHoraAgendada);
            const hoje = new Date();
            const diasParaExecucao = Math.ceil((dataHoraAgendadaObj.getTime() - hoje.getTime()) / (1000 * 3600 * 24));

            let mensagemTaxa = '';

            // Aplicar taxas conforme as condições especificadas
            if (valorTransferencia === 3.0 && this.transferirAgoraVisivel) {
                mensagemTaxa = 'Taxa de 2.5% aplicada.';
            } else if (diasParaExecucao >= 1 && diasParaExecucao <= 10 && this.transferirAgoraVisivel && valorTransferencia === 12.0) {
                this.mensagem = 'Transferência não permitida por taxa de 0.0%.';
                return;
            } else if (diasParaExecucao >= 11 && diasParaExecucao <= 20) {
                mensagemTaxa = 'Taxa de 8.2% aplicada.';
            } else if (diasParaExecucao >= 21 && diasParaExecucao <= 30) {
                mensagemTaxa = 'Taxa de 6.9% aplicada.';
            } else if (diasParaExecucao >= 31 && diasParaExecucao <= 40) {
                mensagemTaxa = 'Taxa de 4.7% aplicada.';
            } else if (diasParaExecucao >= 41 && diasParaExecucao <= 50) {
                mensagemTaxa = 'Taxa de 1.7% aplicada.';
            }

            const tempoParaExecucao = dataHoraAgendadaObj.getTime() - hoje.getTime();

            if (tempoParaExecucao > 0) {
                setTimeout(() => {
                    this.enviarTransferencia(this.transferencia);
                }, tempoParaExecucao);

                this.mensagem = `Transferência agendada para ${dataHoraAgendadaObj.toLocaleString()}. ${mensagemTaxa}`;
            } else {
                this.mensagem = 'Por favor, escolha uma data e hora futuras para agendar a transferência.';
            }
        },

        transferirAgora() {
            this.enviarTransferencia(this.transferenciaImediata);
        },
        enviarTransferencia(transferencia) {
            axios.post('http://localhost:9000/api/transferencias/agendar', {
                contaOrigemId: this.transferencia.contaOrigemId,
                contaDestinoId: transferencia.contaDestinoId,
                valor: transferencia.valor,
                dataAgendada: transferencia.dataAgendada
            }, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('sessionTokenJWT')}`
                }
            })
                .then(response => {
                    this.mensagem = 'Transferência realizada com sucesso!';
                    console.log('Resposta:', response.data);
                })
                .catch(error => {
                    this.mensagem = 'Erro ao realizar transferência.';
                    console.error('Erro:', error);
                });
        },
        buscarContas() {
            axios.get('http://localhost:9000/api/contas')
                .then(response => {
                    this.contas = response.data;
                    this.mostrarListaContas = true;
                })
                .catch(error => {
                    console.error('Erro ao buscar contas:', error);
                });
        },
        selecionarConta(conta) {
            this.transferencia.contaDestinoId = conta.id;
            this.mostrarListaContas = false;
        },
        selecionarContaImediata(conta) {
            this.transferenciaImediata.contaDestinoId = conta.id;
            this.mostrarListaContas = false;
        },
        toggleTransferenciaMode() {
            this.transferirAgoraVisivel = !this.transferirAgoraVisivel;
        }
    }
};
</script>

<style scoped>
.transferencia-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f5f5f5;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.transferencia-form {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.transferir-agora {
    margin-top: 20px;
}

.form-group {
    display: flex;
    align-items: center;
}

label {
    margin-right: 10px;
    font-weight: bold;
}

input {
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    flex: 1;
}

ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    background-color: white;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    max-height: 200px;
    overflow-y: auto;
}

li {
    padding: 8px;
    cursor: pointer;
}

button.button-style {
    margin-bottom: 10px;
    padding: 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button.button-style:focus {
    outline: none;
}

.feedback {
    margin-top: 20px;
    padding: 10px;
    background-color: #d4edda;
    border: 1px solid #c3e6cb;
    border-radius: 4px;
}

.feedback p {
    margin: 0;
    color: #155724;
}

.toggle-icon {
    cursor: pointer;
    margin-bottom: 10px;
}
</style>