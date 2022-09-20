import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.css'
import "gridjs/dist/theme/mermaid.css";
import "bootstrap"
createApp(App)
    .use(router)
    .mount('#app')
