<template>
  <div id="app">
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

  </style>
