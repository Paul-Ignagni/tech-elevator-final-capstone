<template>
  <div class="collection">
    <h2>Public Collections</h2>
    <div
      v-for="collection in collections"
      :key="collection.id"
      class="collection-comic"
    >
      <h3>{{collection.name}}</h3>
      <h5>User ID: {{collection.userId}}</h5>
      <button type="button">View this collection</button>
    </div>
  </div>
</template>


<script>
// import axios from "axios";
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      collections: [],
    };
  },
  props: {
    collection: Array,
    createdCollection: Object,
  },
  created() {
    // Fetch and display all available collections initially
    this.fetchAllCollections();
  },
  methods: {
    fetchAllCollections() {
      ComicService.getPublicCollections().then((response) => {
        this.collections = response.data;
      });
    },
  },
};
</script>

<style>
/* Your styles here */
</style>
