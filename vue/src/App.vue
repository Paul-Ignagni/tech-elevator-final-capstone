<template>
  <div id="app">
    <router-link to="/" class="logo-link">
      <img src="@/components/splash page logo.png" alt="Logo" class="logo-img" />
    </router-link>
    <Sidebar />
    <router-view :collection="collection" />
  </div>
</template>

<script>
import { mapState } from 'vuex';

import Sidebar from './views/Sidebar.vue';

export default {
  components: {Sidebar},
  computed: {
    ...mapState(['collection']),
  },
};
</script>

<style>

body {
 margin: 0px; 
}

.logo-link {
  display: block;
  position: absolute;
  left: 10px;
  z-index: 1000;
}

.logo-img {
  width: 225px;
  height: auto;
}
.sidebar {
  position: fixed;
  background: url(@/components/GrayBackground.jpg);
  background-size: cover;
  background-position: center;
  border-radius: 0px 25px 25px 0px;
  border-style: solid;
  border-color: #000000;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  top: 20%;
  left: 0;
  left: -250px;
  width: 250px;
  height: 250px;
  transition: left 0.3s ease-in-out;
  z-index: 1000; /* Ensure the sidebar is above other content */
}

.sidebar.open {
  left: 0; /* Slide the sidebar into view */
}

.sidebar h2 {
  text-align: center;
  padding: 0px;
  margin: 10px;
  color: #9616ff;
  text-shadow: 2px 2px #000000;
  background: #ffffff;
  font-family: "Comic Sans MS", cursive;
  font-weight: bold;
  border-style: outset;
  border-radius: 5px;
}

.sidebar-nav {
  padding: 0px;
}

.sidebar-nav ul {
  list-style: none;
  padding: 20;
  margin: 20;
}

.sidebar-nav li {
  margin-bottom: 10px;
}

.sidebar-nav a {
  font-family: "Comic Sans MS", cursive;
  color: #ffffff;
  text-decoration: none;
  text-shadow: 2px 2px #000000;
  font-weight: 300;
  font-size: 24px;
  transition: font-size 0.3s, margin-bottom 0.3s;
}

.sidebar-nav a:hover {
  font-weight: 500;
  font-size: 36px;
  margin-bottom: 20px;
}
  </style>
