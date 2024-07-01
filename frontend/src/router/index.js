import { createRouter, createWebHistory } from 'vue-router'
import IndexComponent from '../components/IndexComponent.vue'
import CreateAccount from '../components/CreateAccount.vue'
import ContaUser from '../components/ContaUser.vue'
import TransferenciaComponent from '../components/TransferenciaComponent.vue'
import HistoricoComponent from '../components/HistoricoComponent.vue'

const routes = [
  {
    path: '/',
    name: 'IndexComponent',
    component: IndexComponent
  },
  {
    path: '/create-account',
    name: 'CreateAccount',
    component: CreateAccount
  },
  {
    path: '/conta-user',
    name: 'ContaUser',
    component: ContaUser,
    props: route => ({ id: route.params.id, email: route.params.email })
  },
  {
    path: '/transferencia/:contaOrigemId', // Defina o parâmetro na rota
    name: 'Transferencia',
    component: TransferenciaComponent,
    props: true // Permite passar props como parâmetros
  },
  {
    path: '/historico',
    name: 'Historico',
    component: HistoricoComponent
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
