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
           <button @click="addToCollection(comic)" class="add-to-collection-button">Add to Collection</button>
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
      comics: [],
      collection: []
    };
  },
  methods: {
    searchComics() {
      comicService.search(this.searchQuery).then((response) => {
        this.comics = response.data.data.results;
      });
    },
     addToCollection(comic) {
      this.collection.push(comic);
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
  margin-bottom: 20px;
  color: darkcyan;
}

.search-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.search-input {
  padding: 8px;
  font-size: 16px;
  border: 2px solid #000; 
  border-radius: 10px;
}

.search-button {
  padding: 8px 16px;
  font-size: 16px;
  background-color: crimson; 
  color: #FFF; 
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.search-button:hover {
  background-color: #990000; 
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
.home {
  /* Set the background gradient */
  background: linear-gradient(45deg, pink 50%, lightblue 50%);
  padding: 20px;
  color: white;
  position: relative;
}
.add-to-collection-button {
  margin-top: 10px;
  padding: 8px 16px;
  font-size: 14px;
  background-color: crimson; /* Set background color to crimson */
  color: white; /* Set text color to white */
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  animation: breathing 4s infinite;
}

.add-to-collection-button:hover {
  background-color: #9C0017; /* Darker crimson on hover */
  animation: none;
}
@keyframes breathing {
   0% { background-color: #FFD700; } /* Gold color */
  50% { background-color: #FFB400; } /* Darker gold color */
  100% { background-color: #FFD700; } /* Return to gold color */

}
</style>