<template>
  <div class = "comic">
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
      <p>Authors:</p>
      <div v-for="author in authors" :key="author">
        {{author}}
        </div>

    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      comic: [],
      authors: [],
    };
  },
  created() {
    this.fetchComicInfo(1);
    //The 1 is a temporary placeholder for the comic's id, need to add dynamic variable for different comics
    this.fetchAuthors(1);
  },
  methods: {
    fetchComicInfo(serial) {
      ComicService.getComicInfo(serial).then((response) => {
        this.comic = response.data;
      });
    },
    fetchAuthors(serial) {
      ComicService.getAuthorsForComic(serial).then((response) => {
        this.authors = response.data;
      });
    },
  },
};
</script>

<style>
.comic {
  text-align: center;
}
</style>