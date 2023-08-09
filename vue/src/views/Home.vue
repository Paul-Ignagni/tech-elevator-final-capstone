<template>
  <div class="home">
    <h1 class="comic-heading">Comic Book Search</h1>
    <div class="search-container">
      <input v-model="searchQuery" placeholder="Enter comic name" class="search-input" />
      <button @click="searchComics" class="search-button">Search</button>
    </div>
    <div class="search-results">
      <div v-for="comic in comics" :key="comic.id" class="comic-card">
        <div class="comic-cover">
          <img v-if="comic.images && comic.images.length > 0" :src="comic.images[0].path + '.' + comic.images[0].extension" alt="Comic Cover" />
        </div>
        <div class="comic-details">
          <h2>{{ comic.title }}</h2>
          <p v-if="comic.description">{{ comic.description }}</p>
          <p>Issue Number: {{ comic.issueNumber }}</p>
          <p>Page Count: {{ comic.pageCount }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import comicService from "../services/ComicService.js";

export default {
  name: "home",
  data() {
    return {
      searchQuery: "",
      comics: []
    };
  },
  methods: {
    searchComics() {
      comicService.search(this.searchQuery).then((response) => {
        this.comics = response.data.data.results;
      });
    }
  }
};
</script>

<style>

.home {
  text-align: center;
}

.comic-heading {
  font-family: 'Comic Sans MS', cursive; /* Use a comic book font here */
  font-size: 28px;
}

.search-container {
  margin-top: 20px;
}

.search-input {
  padding: 8px;
  font-size: 16px;
}

.search-button {
  padding: 8px 16px;
  font-size: 16px;
}

.comic-card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  display: inline-block;
  max-width: 200px;
}

.comic-card img {
  max-width: 100%;
  height: auto;
}
</style>