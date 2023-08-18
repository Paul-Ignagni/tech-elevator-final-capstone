<template>
  <div class = "creator">
    <div class="creator-details">
      <h1>{{ creator.creatorName }}</h1>
    <h3>Works: </h3>
    <div v-for="comic in comics" :key="comic" class = "comics">
      <p> {{comic}} </p>
      </div>
    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      creator: [],
      comics: [],
    };
  },
  created() {
    this.fetchCreatorInfo(1);
    //The 1 is a temporary placeholder for the character's id, need to add dynamic variable for different characters
    this.fetchCreatorComics(1);
  },
  methods: {
    fetchCreatorInfo(serial) {
      ComicService.getCreatorInfo(serial).then((response) => {
        this.creator = response.data;
      });
    },
    fetchCreatorComics(serial) {
      ComicService.getCreatorComics(serial).then((response) => {
        this.comics = response.data;
      })
    }
  },
};
</script>

<style>
.creator {
    text-align: center;
}
</style>