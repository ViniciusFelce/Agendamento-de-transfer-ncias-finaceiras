<template>
  <b-container>
    <b-row>
      <b-col>
        <b-card title="Criar Conta" class="my-3">
          <b-form @submit.prevent="onSubmit">
            <b-form-group label="Nome" label-for="nome">
              <b-form-input id="nome" v-model="formData.cliente.nome" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="CPF" label-for="cpf">
              <b-form-input id="cpf" v-model="formData.cliente.cpf" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="Endereço" label-for="endereco">
              <b-form-input id="endereco" v-model="formData.cliente.endereco" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="E-mail" label-for="email">
              <b-form-input id="email" type="email" v-model="formData.email" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-form-group label="Senha" label-for="senha">
              <b-form-input id="senha" type="password" v-model="formData.password" required class="custom-input"></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary">Enviar</b-button>
          </b-form>
          <b-alert v-if="errorMessage" variant="danger" dismissible>
            {{ errorMessage }}
          </b-alert>
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
        email: '',
        password: '',
        cliente: {
          nome: '',
          cpf: '',
          endereco: '',
          contas: [
            {
              agencia: '0001',
              banco: 'BlueStar Bank',
              numero: '', // Será gerado automaticamente
              saldo: 1000.00 // limite de crédito
            }
          ]
        }
      },
      errorMessage: ''
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
      this.formData.cliente.contas[0].numero = this.generateRandomNumber(16);
      axios.post('http://localhost:9000/api/auth/signup', this.formData)
        .then(response => {
          console.log('Conta criada com sucesso:', response.data);
          this.$router.push('/'); // Redireciona para a tela de login após o sucesso
        })
        .catch(error => {
          console.error('Erro ao criar conta:', error);
          if (error.response) {
            if (error.response.status === 400) {
              this.errorMessage = 'Erro nos dados enviados!';
            } else if (error.response.status === 409) {
              this.errorMessage = 'Email já registrado!';
            } else {
              this.errorMessage = 'Erro ao enviar formulário!';
            }
          } else {
            this.errorMessage = 'Erro de conexão!';
          }
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
  margin: 0 auto;
}

.create-account-text {
  font-size: 1rem;
  color: #989898;
  margin-top: 1rem;
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
