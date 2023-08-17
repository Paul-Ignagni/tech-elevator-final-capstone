<template>
  <div class="collection">
    <h2>Public Collections</h2>

    <!-- Search bars for character and creator -->
    <div class="search-bar">
      <label for="characterSearch">Search by Character:</label>
      <input v-model="characterSearchKeyword" id="characterSearch" placeholder="Enter character name">
      <button @click="searchComicsByCharacter">Search</button>
      <p v-if="characterKeywordComicCount !== null">
        Total comics with character "{{ characterSearchKeyword }}": {{ characterKeywordComicCount }}
      </p>

      <label for="creatorSearch">Search by Creator:</label>
      <input v-model="creatorSearchKeyword" id="creatorSearch" placeholder="Enter creator name">
      <button @click="searchComicsByCreator">Search</button>
      <p v-if="creatorKeywordComicCount !== null">
        Total comics with creator "{{ creatorSearchKeyword }}": {{ creatorKeywordComicCount }}
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
      characterSearchKeyword: "",
      characterKeywordComicCount: null,
      creatorSearchKeyword: "",
      creatorKeywordComicCount: null,
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

     searchComicsByCharacter() {
      ComicService.getTotalComicsByCharacter(this.characterSearchKeyword).then(response => {
        this.characterKeywordComicCount = response.data;
      }).catch(error => {
        console.error("Error searching for comics:", error);
      });
    },

    searchComicsByCreator() {
      ComicService.getTotalComicsByCreator(this.creatorSearchKeyword).then(response => {
        this.creatorKeywordComicCount = response.data;
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