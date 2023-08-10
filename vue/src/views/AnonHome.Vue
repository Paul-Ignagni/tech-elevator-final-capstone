<template>
  <div class="anon-home">
    <h1 class="comic-heading">Comic Book Collections</h1>
    <div class="search-results">
      <div v-for="collection in collections" :key="collection.id" class="collection-card">
        <div class="collection-details">
          <h2>{{ collection.title }}</h2>
          <p>{{ collection.description }}</p>
          <p>Total Comics: {{ collection.comics.length }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import comicService from "../services/ComicService.js";
import { gsap } from "gsap";


export default {
  name: "anon-home",
  data() {
    return {
      collections: [] // This array will hold all collections fetched
    };
  },
  created() {
    // Fetch and display all collections
    this.fetchCollections();
  },
  methods: {
    fetchCollections() {
//will add method here once i figure out how to differentiate non-public collections
      // });
    }
  }
};
</script>

<style scoped>
.anon-home {
  text-align: center;
  background: linear-gradient(45deg, pink 50%, lightblue 50%);
  padding: 20px;
  color: white;
  position: relative;
}

.comic-heading {
  font-family: 'Comic Sans MS', cursive;
  font-size: 28px;
  margin-bottom: 20px;
  color: darkcyan;
}

.collection-card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  display: inline-block;
  max-width: 300px;
  overflow: hidden;
  background-color: #ffffff;
  color: #000000;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.collection-card:hover {
  transform: translateY(-5px) scale(1.05);
  box-shadow: 0px 6px 8px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

.collection-details {
  padding: 10px;
}

</style>