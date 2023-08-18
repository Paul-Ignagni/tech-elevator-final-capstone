<template>
  <div class="collection">
    <h1>Collection Name</h1>
    <h2>Comics:</h2>
    <div v-for="comic in comicsArr" :key="comic.serial" class="collection-comic">
      <h3>{{ comic.title }}</h3>
      <div class="comic-cover">
        <img
          v-if="comic.images && comic.images.length > 0"
          :src="comic.images" alt="ComicCover"/>
        <div v-else class="blank-comic-card">
          <p class="image-not-available">Image not available</p>
        </div>
      </div>
      <div class="comic-details">
        <p v-if="comic.description">Description: {{ comic.description }}</p>
        <p>Issue Number: {{ comic.issueNumber }}</p>
        <p v-if="comic.pageCount">Page Count: {{ comic.pageCount }}</p>
      </div>
    </div>
    <div>
      <div>
      <h2>Collection Statistics</h2>
      <h4>Number of comics in collection: {{totalComics}} </h4>
      </div>
      <div>
      <h4>Series in this collection:</h4>
      <p v-for="s in series" :key="s" class="series">
        {{s}}
        </p>
      </div>
      <div>
      <h4>Characters that appear in this collection:</h4>
      <p v-for="c in characters" :key="c" class="character">
        {{c}}
        </p>
      </div>
      <div>
      <h4>Authors in this collection:</h4>
      <p v-for="cr in creators" :key="cr" class="creator">
        {{cr}}
        </p>
      </div>
    </div>
  </div>

</template>


<script>
// import axios from "axios";
import comicService from "../services/ComicService.js";

export default {
      totalComics: 0,
      collectionId: 1,
  data() {
    return {
      
      comicsArr: [],
      series: [],
      characters: [],
      creators:[],
    };
  },
  props: {
    collection: Array,
    createdCollection: Object,
  },
  created() {
    this.getCollectionComics(1);
    //THE COLLECTION ID IS JUST A PLACEHOLDER. ADD VARIABLE FOR DIFFERENT COLLECTION ID NUMBERS ON BUTTON CLICK LATER
    this.getSeries(1);
    this.getCharacters(1);
    this.getCreators(1);
  },
  mounted() {
    this.getTotalComics(this.comicsArr);
  },
  methods: {
    getCollectionComics(collectionId) {
      comicService.getComicsInCollections(collectionId).then((response) => {
        this.comicsArr = response.data;
      });
    },
    getTotalComics(comicsArr) {
      this.totalComics = comicsArr.length
      console.log(comicsArr)
    },
    getSeries(collectionId) {
      comicService.getSeriesInCollection(collectionId).then((response) => {
        this.series = response.data;
      })
    },
    getCharacters(collectionId) {
      comicService.getCharactersInCollection(collectionId).then((response) => {
        this.characters = response.data;
      })
    },
    getCreators(collectionId) {
      comicService.getCreatorsInCollection(collectionId).then((response) => {
        this.creators = response.data;
      })
    }
  },
};
</script>

<style>
.collection {
  text-align: center;
}
</style>
