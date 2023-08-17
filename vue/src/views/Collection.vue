<template>
  <div class="collection">
    <h1>Collection Name</h1>
    <h2>Comics:</h2>
    <div v-for="comic in comicsArr" :key="comic.serial" class="collection-comic">
      <div class="comic-cover">
        <!-- <img
          v-if="comic.images && comic.images.length > 0"
          :src="comic.images[0].path + '.' + comic.images[0].extension"
          alt="Comic Cover"
        /> -->
        <h5>({{comic.images}}) </h5>
        <!-- <div v-else class="blank-comic-card">
          <p class="image-not-available">Image not available</p>
        </div> -->
      </div>
      <div class="comic-details">
        <h3>{{ comic.title }}</h3>
        <p v-if="comic.description">Description: {{ comic.description }}</p>
        <p>Issue Number: {{ comic.issueNumber }}</p>
        <p>Page Count: {{ comic.pageCount }}</p>
      </div>
    </div>
    <div>
      <h2>Collection Stats</h2>
      <p> Number of comics in collection: {{totalComics}} </p>
      <p> Series in this collection: To be added </p>
      <p> Characters that appear in this collection: To be added</p>
      <p> Authors in this collection: To be added</p>
    </div>
  </div>

</template>


<script>
// import axios from "axios";
import comicService from "../services/ComicService.js";

export default {
  data() {
    return {
      comicsArr: [],
      totalComics: 0,
    };
  },
  props: {
    collection: Array,
    createdCollection: Object,
  },
  created() {
    this.getCollectionComics(1);
    //THE COLLECTION ID IS JUST A PLACEHOLDER. ADD VARIABLE FOR DIFFERENT COLLECTION ID NUMBERS ON BUTTON CLICK LATER
    //
    //
    //
    //
    //
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
    },
  },
};
</script>

<style>
.collection {
  text-align: center;
}
</style>
