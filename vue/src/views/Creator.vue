<template>
  <div class = "creator">
    <div class="creator-details">
      <h2>{{ creator.creatorName }}</h2>
    <p>Works: </p>
    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      creator: [],
    };
  },
  created() {
    this.fetchCreatorInfo(1);
    //The 1 is a temporary placeholder for the character's id, need to add dynamic variable for different characters
  },
  methods: {
    fetchCreatorInfo(serial) {
      ComicService.getCreatorInfo(serial).then((response) => {
        this.creator = response.data;
      });
    },
  },
};
</script>

<style>
.creator {
    text-align: center;
}
</style>