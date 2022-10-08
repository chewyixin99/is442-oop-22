import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import "gridjs/dist/theme/mermaid.css";
import 'bootstrap-icons/font/bootstrap-icons.css'
import 'bootstrap'
import "bootstrap/dist/css/bootstrap.min.css"
createApp(App)
    .use(router)
    .mount('#app')
