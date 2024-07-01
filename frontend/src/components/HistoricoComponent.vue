<template>
  <div>
    <h1>Histórico de Transações</h1>
    <!-- Verifique se há transações para exibir -->
    <div v-if="transacoes.length">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Conta de Origem</th>
            <th>Conta de Destino</th>
            <th>Valor</th>
            <th>Data Agendada</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transacao in transacoes" :key="transacao.id">
            <td>{{ transacao.id }}</td>
            <td>{{ transacao.contaOrigemId }}</td>
            <td>{{ transacao.contaDestinoId }}</td>
            <td>{{ transacao.valor }}</td>
            <td>{{ transacao.dataAgendada }}</td>
            <td>{{ transacao.status }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <p v-else>Nenhuma transação encontrada.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HistoricoComponent',
  data() {
    return {
      transacoes: []
    };
  },
  created() {
    this.obterHistoricoTransacoes();
  },
  methods: {
    obterHistoricoTransacoes() {
      axios.get('http://localhost:9000/api/transferencias', {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('sessionTokenJWT')}`
        }
      })
      .then(response => {
        this.transacoes = response.data;
      })
      .catch(error => {
        console.error('Erro ao obter histórico de transações:', error);
      });
    }
  }
}
</script>

<style scoped>
/* Estilos para o componente de histórico */
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f2f2f2;
}

tbody tr:hover {
  background-color: #f5f5f5;
}
</style>
