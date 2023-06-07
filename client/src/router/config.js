import VueRouter from 'vue-router'
import InputJson from "../components/InputJson.vue";

export default new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: InputJson
        }
    ]
})