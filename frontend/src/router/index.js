import { createRouter, createWebHistory } from 'vue-router'
import IndexComponent from '../components/IndexComponent.vue'
import CreateAccount from '../components/CreateAccount.vue'
import ContaUser from '../components/ContaUser.vue'

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
    component: ContaUser
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
