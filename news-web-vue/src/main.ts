import { createApp } from 'vue'
import App from './App.vue'
import axios from './utils/axios'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'

const app = createApp(App)
app.config.globalProperties.$axios = axios
app.use(Antd).use(store).use(router).mount('#app')
