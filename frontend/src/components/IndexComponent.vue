<template>
  <div class="index-container">
    <h1>Bem-vindo ao BlueStar Bank</h1>
    <p>
      O BlueStar Bank oferece uma experiência bancária excepcional, projetada para facilitar sua vida financeira. Com serviços inovadores e uma plataforma fácil de usar, você pode criar sua conta bancária de forma rápida e segura.
    </p>
    <p>
      Junte-se aos milhares de clientes satisfeitos que já escolheram o BlueStar Bank. Clique no botão abaixo e comece a usar nossos serviços hoje mesmo!
    </p>
    <hr class="custom-hr">
    <h2>Login</h2>
    <b-form @submit.prevent="onLogin" class="login-form">
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
          // Armazenar dados no localStorage
          localStorage.setItem('sessionTokenJWT', response.data.token);
          localStorage.setItem('userId', response.data.id);
          localStorage.setItem('userEmail', response.data.email);
          this.$router.push({ name: 'ContaUser' }); // Redirecionar para a página após o login
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

.custom-hr {
  margin: 20px auto;
  width: 50%;
}

.login-form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.custom-input {
  width: 100%;
  margin: 0 auto;
}

.create-account-text {
  margin-top: 15px;
}
</style>
