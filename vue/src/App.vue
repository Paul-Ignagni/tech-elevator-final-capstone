<template>
  <div id="app">
    <router-link to="/" class="logo-link">
      <img src="@/components/Marvel Comics logo.png" alt="Logo" class="logo-img" />
    </router-link>
    <router-view :collection="collection" />
  </div>
</template>

<script>
import { mapState } from 'vuex';



export default {
 
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

  </style>
