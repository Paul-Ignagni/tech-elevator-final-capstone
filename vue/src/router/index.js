import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Collection from '../views/Collection.vue'
import Collections from '../views/Collections.vue'
import CreateCollection from '../views/CreateCollection.vue'
import MyCollection from '../views/MyCollection.vue'
import Comic from '../views/Comic.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/comic/:comicId',
      name: 'Comic',
      component: Comic,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections',
      name: 'collections',
      component: Collections,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/:id',
      name: 'collection',
      component: Collection,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/collections/myCollection/:userId',
      name: 'myCollection',
      component: MyCollection,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/collections/create',
      name: 'createCollection',
      component: CreateCollection,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  if (requiresAuth && !store.state.token) {
    next("/login");
  } else if (!requiresAuth && store.state.token && (to.name === 'login' || to.name === 'register')) {
    next("/collection");
  } else {
    next();
  }
});

export default router;