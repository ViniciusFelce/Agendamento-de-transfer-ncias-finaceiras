<template>
  <div class="index-container">
    <h1>Bem-vindo à Criação de Conta Bancária</h1>
    <p>
      Aqui você pode criar sua conta bancária de forma fácil e rápida.
      Preencha os dados necessários e comece a usar nossos serviços.
    </p>
    <p>
      Nossos serviços são projetados para oferecer a melhor experiência
      para nossos clientes. Comece agora mesmo clicando no botão abaixo.
    </p>
    <hr class="custom-hr">
    <h2>Login</h2>
    <b-form @submit.prevent="onLogin">
      <b-form-group label="Email" label-for="email">
        <b-form-input id="email" v-model="loginData.email" type="email" required class="custom-input"></b-form-input>
      </b-form-group>
      <b-form-group label="Senha" label-for="password">
        <b-form-input id="password" v-model="loginData.password" type="password" required class="custom-input"></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="success" class="mb-3">Entrar</b-button>
      <p class="create-account-text">
        Não tem uma conta? <router-link to="/create-account">Criar Conta</router-link>
      </p>
    </b-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'IndexComponent',
  data() {
    return {
      loginData: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    onLogin() {
      axios.post('http://localhost:9000/api/auth/signin', {
        email: this.loginData.email,
        password: this.loginData.password
      })
      .then(response => {
        console.log('Resposta do login:', response.data);
        if (response.status === 200) {
          this.$router.push('/conta-user');
        } else {
          console.error('Erro ao fazer login:', response);
        }
      })
      .catch(error => {
        console.error('Erro ao fazer login:', error);
      });
    }
  }
}
</script>

<style scoped>
.index-container {
  text-align: center;
  padding: 20px;
}

.index-container h1 {
  font-size: 2.5rem;
  margin-bottom: 20px;
}

.index-container p {
  font-size: 1.2rem;
  margin-bottom: 15px;
}

.index-container b-button {
  font-size: 1rem;
  padding: 10px 20px;
}

.index-container h2 {
  margin-top: 40px;
  font-size: 2rem;
  margin-bottom: 20px;
}

.custom-input {
  width: 35%; /* Ajuste a largura conforme sua preferência */
  margin: 0 auto;
}

.custom-hr {
  border: 0;
  border-top: 1px solid #323232;
  margin: 20px 0;
}

.create-account-text {
  font-size: 1rem; /* Tamanho da fonte ajustado */
  color: #989898;
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
