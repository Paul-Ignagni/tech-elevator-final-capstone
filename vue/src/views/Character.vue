<template>
  <div class = "character">
    <div :key="character.characterSerial" class="character-image">
      <img
        v-if="character.characterImage && character.characterImage.length > 0"
        :src="character.characterImage"
        alt="Character Image"
      />
      <div v-else class="blank-character-card">
        <p class="image-not-available">Image not available</p>
      </div>
    </div>
    <div class="character-details">
      <h2>{{ character.characterName }}</h2>
      <p v-if="character.characterDescription">{{ character.characterDescription }}</p>
    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      character: [],
    };
  },
  created() {
    this.fetchCharacterInfo(1);
    //The 10 is a temporary placeholder for the character's id, need to add dynamic variable for different characters
  },
  methods: {
    fetchCharacterInfo(serial) {
      ComicService.getCharacterInfo(serial).then((response) => {
        this.character = response.data;
      });
    },
  },
};
</script>

<style>
.character {
    text-align: center;
}
</style>