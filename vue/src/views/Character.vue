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
      characterId: this.$route.params.serial,
    };
  },
  created() {
    this.fetchCharacterInfo(this.characterId);
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
.character-details {
  font-size: 32px
}
</style>