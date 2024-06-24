<template>
  <b-navbar toggleable="lg" type="dark" variant="info" class="fixed-top">
    <b-navbar-brand href="#">BlueStar Bank</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#">Home</b-nav-item>
        <b-nav-item href="#/create-account">Criar Conta</b-nav-item>
        <b-nav-item href="#">Sobre</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  name: 'HeaderBar'
}
</script>

<style scoped>
/* O estilo .b-navbar não é necessário para fixar o navbar, mas pode ser ajustado conforme necessário */
.b-navbar {
  margin-bottom: 10px; /* Ajuste a margem inferior conforme necessário */
}
</style>
