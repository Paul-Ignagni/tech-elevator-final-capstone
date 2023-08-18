<template>
  <div class="collection">
    <h1>{{collection.name}}</h1>
    <h3>User: {{username}}</h3>
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
      <h4 v-if="series.length > 0">Series in this collection:</h4>
      <p v-for="s in series" :key="s" class="series">
        {{s}}
        </p>
      </div>
      <div>
      <h4 v-if="characters.length > 0">Characters that appear in this collection:</h4>
      <p v-for="c in characters" :key="c" class="character">
        {{c}}
        </p>
      </div>
      <div>
      <h4 v-if="creators.length > 0" >Authors in this collection:</h4>
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
  data() {
    return {
      totalComics: 0,
      comicsArr: [],
      series: [],
      characters: [],
      creators:[],
      collection: [],
      username: "",
      collectionId: this.$route.params.id,
    };
  },
  props: {
    createdCollection: Object,
  },
  created() {
    this.getCollection(this.collectionId);
    this.getCollectionComics(this.collectionId);
    this.getSeries(this.collectionId);
    this.getCharacters(this.collectionId);
    this.getCreators(this.collectionId);
    this.getTotalComics(this.collectionId);
    this.getUsername(this.collectionId);
  },
  methods: {
    getCollection(collectionId) {
      comicService.getCollection(collectionId).then((response) => {
        this.collection = response.data;
      });
    },
    getCollectionComics(collectionId) {
      comicService.getComicsInCollections(collectionId).then((response) => {
        this.comicsArr = response.data;
      });
    },
    getTotalComics(collectionId) {
      comicService.getTotalComicsInOneCollection(collectionId).then((response) => {
        this.totalComics = response.data;
      });
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
    },
    getUsername(collectionId) {
      comicService.getUsernameFromCollectionId(collectionId).then((response) => {
        this.username = response.data;
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
