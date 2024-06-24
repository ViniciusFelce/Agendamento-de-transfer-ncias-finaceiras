<template>
    <footer class="footer">
      <p>&copy; 2024 Seu Nome ou Empresa. Todos os direitos reservados.</p>
    </footer>
  </template>
  
  <script>
  export default {
    name: 'FooterBar'
  }
  </script>
  
  <style scoped>
  .footer {
    background-color: #323232;
    color: #ffffff;
    text-align: center;
    padding: 0.1rem;
    position: fixed;
    width: 100%;
    bottom: 0;
    left: 0;
  }
  @media (max-width: 767px) {
  .footer {
    padding: 0.1rem;
  }
  p {font-size: 13px;}
}

  </style>
  