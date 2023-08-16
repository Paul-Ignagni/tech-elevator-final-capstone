<template>
  <div>
    <div :key="comic.serial" class="comic-cover">
      <img
        v-if="comic.images && comic.images.length > 0"
        :src="comic.images"
        alt="Comic Cover"
      />
      <div v-else class="blank-comic-card">
        <p class="image-not-available">Image not available</p>
      </div>
    </div>
    <div class="comic-details">
      <h2>{{ comic.title }}</h2>
      <p v-if="comic.description">{{ comic.description }}</p>
      <p>Issue Number: {{ comic.issueNumber }}</p>
      <p>Series: {{comic.series}}</p>
      <p>Page Count: {{ comic.pageCount }}</p>

    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      comic: [],
    };
  },
  created() {
    this.fetchComicInfo(1);
    //The 1 is a temporary placeholder for the comic's id, need to add dynamic variable for different comics
  },
  methods: {
    fetchComicInfo(serial) {
      ComicService.getComicInfo(serial).then((response) => {
        this.comic = response.data;
      });
    },
  },
};
</script>

<style>
</style>