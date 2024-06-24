<template>
  <b-container>
    <b-row>
      <b-col>
        <b-card title="Criar Conta" class="my-3">
          <b-form @submit.prevent="onSubmit">
            <b-form-group label="Nome" label-for="nome">
              <b-form-input id="nome" v-model="formData.nome" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="CPF" label-for="cpf">
              <b-form-input id="cpf" v-model="formData.cpf" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="Endereço" label-for="endereco">
              <b-form-input id="endereco" v-model="formData.endereco" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="E-mail" label-for="email">
              <b-form-input id="email" type="email" v-model="formData.email" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="Senha" label-for="senha">
              <b-form-input id="senha" type="password" v-model="formData.senha" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary">Enviar</b-button>
          </b-form>
        </b-card>
        <p class="create-account-text">
          Já tem uma conta? <router-link to="/">Fazer login</router-link>
        </p>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CreateAccount',
  data() {
    return {
      formData: {
        nome: '',
        cpf: '',
        endereco: '',
        email: '',
        senha: '',
        contas: [
          {
            agencia: '0001',
            banco: 'BlueStar Bank',
            numero: '',
            saldo: 1000.00 // limite de crédito
          }
        ]
      }
    }
  },
  methods: {
    generateRandomNumber(length) {
      let result = '';
      const characters = '0123456789';
      for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      return result;
    },
    onSubmit() {
      this.formData.contas[0].numero = this.generateRandomNumber(16);
      axios.post('http://localhost:9000/api/clientes', this.formData)
        .then(response => {
          console.log('Formulário enviado:', response.data);
          this.$router.go(-1); // Volta para a tela anterior após o envio bem-sucedido
        })
        .catch(error => {
          console.error('Erro ao enviar formulário:', error);
          // Adicione aqui qualquer lógica de tratamento de erro
        });
    }
  }
}
</script>

<style scoped>
.my-3 {
  margin-top: 1rem;
  margin-bottom: 1rem;
}

.custom-input {
  width: 35%;
  margin: 0 auto; /* Para centralizar horizontalmente */
}

.create-account-text {
  font-size: 1rem; /* Aumenta o tamanho da fonte */
  color: #989898;
  margin-top: 1rem; /* Espaçamento superior */
}

.create-account-text a {
  color: #007bff;
  text-decoration: none;
}

.create-account-text a:hover {
  text-decoration: underline;
}

@media (max-width: 767px) {
  .custom-input {
    width: 95%;
  }
}
</style>
