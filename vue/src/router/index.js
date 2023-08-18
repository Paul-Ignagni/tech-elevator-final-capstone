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
import Characters from '../views/Characters.vue'
import Character from '../views/Character.vue'
import Creators from '../views/Creators.vue'
import Creator from '../views/Creator.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home, // Redirect authenticated users to the Home page
      meta: {
        requiresAuth: false,
      },
    },
    {
      path: '/collections',
      name: 'collections',
      component: Collections, // Unauthenticated users' home page
      meta: {
        requiresAuth: false,
      },
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
      path: '/characters',
      name: 'Characters',
      component: Characters,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/characters/:serial',
      name: 'Character',
      component: Character,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/creators',
      name: 'Creators',
      component: Creators,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/creators/:creatorSerial',
      name: 'Creator',
      component: Creator,
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
      path: '/collections/myCollection',
      name: 'myCollection',
      component: MyCollection,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/collections/collection/create',
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
    // Redirect unauthenticated users to the Collections page
    next("/collections");
  } else {
    next();
  }
});

export default router;