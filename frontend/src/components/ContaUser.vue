<template>
  <div class="conta-user-container">
    <h1>Olá!, {{ userData.nome }}</h1>
    <div v-if="userData.contas.length > 0">
      <hr>
      <h2>Conta</h2>
      <div v-for="conta in userData.contas" :key="conta.id">
        <p>
          Saldo: 
          <span v-if="!saldoBloqueado">
            R$ {{ conta.saldo.toFixed(2) }}
          </span>
          <span v-else>
            **********
          </span>
          <svg
            @click="toggleBloqueioSaldo"
            :class="{ 'eye-open': !saldoBloqueado, 'eye-closed': saldoBloqueado }"
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-eye"
            viewBox="0 0 16 16"
          >
            <path
              d="M8 4a4 4 0 1 0 0 8a4 4 0 0 0 0-8zm0 2a2 2 0 1 1 0 4a2 2 0 0 1 0-4z"
            />
            <path
              fill-rule="evenodd"
              d="M1.643 7.5a7.5 7.5 0 0 1 12.722-4.272l-1.39 1.39a6 6 0 0 0-9.943 2.882l-1.389 1.39zm12.603 1.39a6 6 0 0 0-9.943-2.882L2.757 6.228A7.5 7.5 0 0 1 14.246 7.5z"
            />
          </svg>
        </p>
        <!-- Nova seção de botões -->
        <div class="button-section">
          <button @click="navigateToTransferencia(conta.id)">Transferir Dinheiro</button>
        </div>
      </div>
      <div class="button-section">
        <button @click="navigateToHistorico">Histórico de Transações</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ContaUser',
  data() {
    return {
      userData: {
        id: '',
        nome: '',
        cpf: '',
        endereco: '',
        contas: []
      },
      saldoBloqueado: true
    }
  },
  mounted() {
    this.fetchUserData();
  },
  methods: {
    fetchUserData() {
      const userId = localStorage.getItem('userId');
      axios.get(`http://localhost:9000/api/clientes/${userId}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('sessionTokenJWT')}`
        }
      })
      .then(response => {
        this.userData = response.data;
        console.log('Dados do usuário:', response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar dados do usuário:', error);
      });
    },
    toggleBloqueioSaldo() {
      this.saldoBloqueado = !this.saldoBloqueado;
    },
    navigateToTransferencia(contaOrigemId) {
      this.$router.push({ name: 'Transferencia', params: { contaOrigemId } });
    },
    navigateToHistorico() {
      this.$router.push({ name: 'Historico' });
    }
  }
}
</script>

<style scoped>
.conta-user-container {
  padding: 20px;
}

/* Estilos para os ícones de olho */
.eye-open {
  cursor: pointer;
  color: #007bff; /* Azul do Bootstrap */
}
.eye-closed {
  cursor: pointer;
  color: #6c757d; /* Cinza do Bootstrap */
}

/* Estilos para a seção de botões */
.button-section {
  margin-top: 20px;
}

.button-section button {
  margin-right: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.button-section button:hover {
  background-color: #0056b3;
}
</style>
