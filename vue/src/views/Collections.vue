<template>
  <div class="collection">
    <h2>Public Collections</h2>

    <!-- Search bar -->
    <div class="search-bar">
      <input v-model="searchKeyword" placeholder="Search comics by keyword">
      <button @click="searchComicsWithKeyword">Search</button>
      <p v-if="keywordComicCount !== null">
        Total comics with keyword "{{ searchKeyword }}": {{ keywordComicCount }}
      </p>
      <p v-else-if="searchKeyword">
        Sorry, no comics found with keyword "{{ searchKeyword }}". Maybe you should write one?
      </p>
    </div>

    <!-- Button to calculate total comics -->
    <button @click="calculateTotalAllComics">How many comics are there in all the collections?</button>
    <p v-if="totalAllComics !== null">
      Total comics in all collections: {{ totalAllComics }}
    </p>

    <!-- Display collections -->
    <div
      v-for="collection in collections"
      :key="collection.id"
      class="collection-comic"
    >
      <h3>{{ collection.name }}</h3>
      <h5>User ID: {{ collection.userId }}</h5>
      <button type="button">View this collection</button>
    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      collections: [],
      searchKeyword: "",
      keywordComicCount: null,
      totalAllComics: null
    };
  },

  created() {
    // Fetch and display all available collections initially
    this.fetchAllCollections();
  },

  methods: {
    fetchAllCollections() {
      ComicService.getPublicCollections().then(response => {
        this.collections = response.data;
      });
    },

    searchComicsWithKeyword() {
      ComicService.searchComicsByKeyword(this.searchKeyword).then(response => {
        const comicData = response.data.data;
        if (comicData && comicData.results.length > 0) {
          this.keywordComicCount = comicData.results.length;
        } else {
          this.keywordComicCount = 0;
        }
      }).catch(error => {
        console.error("Error searching for comics:", error);
      });
    },

    calculateTotalAllComics() {
      const total = this.collections.reduce((total, collection) => {
        return total + collection.comicCount;
      }, 0);
      this.totalAllComics = total;
    }
  }
};
</script>

<style>
/* Your styles here */
</style>