<template>
  <div class="collection">
    <h2>My Collections</h2>
    <div
      v-for="collection in collections"
      :key="collection.id"
      class="collection-comic"
    >
      <h3>{{collection.name}}</h3>
      <h5>User ID: {{collection.userId}}</h5>
      <button type="button">View this collection</button>
    </div>
    <!-- Button to route to /collections/create -->
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
    this.fetchMyCollections(1);
    //The 1 is a temporary placeholder for the active user's id, need to add dynamic variable for different collections
  },
  methods: {
    fetchMyCollections(userId) {
      ComicService.getUserCollections(userId).then((response) => {
        this.collections = response.data;
      });
    },
    
    
  },
};
</script>

<style>
/* Your styles here */
</style>
