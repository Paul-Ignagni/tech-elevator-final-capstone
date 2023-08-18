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
      creatorId: this.$route.params.creatorSerial,
    };
  },
  created() {
    this.fetchCreatorInfo(this.creatorId);
    this.fetchCreatorComics(this.creatorId);
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
    text-shadow: 2px 2px #000000;
}
.creator-details {
    font-size: 32px;
    background: url(@/components/GrayBackground.jpg);
    text-shadow: 2px 2px #000000;
    font-family: "Comic Sans MS", cursive;
    
}
</style>