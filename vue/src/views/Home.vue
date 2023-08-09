<template>
  <div class="home">
    <h1>Comic Book Search</h1>
    <div>
      <input v-model="searchQuery" placeholder="Enter comic name" />
      <button @click="searchComics">Search</button>
    </div>
    <div class="search-results">
      <div v-for="comic in comics" :key="comic.id" class="comic-card">
        <img v-if="comic.images && comic.images.length > 0" :src="comic.images[0].path + '.' + comic.images[0].extension" alt="Comic Cover" />
        <h2>{{ comic.title }}</h2>
        <p v-if="comic.description">{{ comic.description }}</p>
        <p>Issue Number: {{ comic.issueNumber }}</p>
        <p>Page Count: {{ comic.pageCount }}</p>
        <div v-for="image in comic.images" :key="image.path">
          <img :src="image.path + '.' + image.extension" alt="Comic Cover" />
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
/* Add your styles here */
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