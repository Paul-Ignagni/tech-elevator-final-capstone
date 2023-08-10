<template>
  <div class="collection">
    <h2>Your Collection</h2>
  <div v-for="comic in collection" :key="comic.id" class="collection-comic">
    <div class="comic-cover">
      <img v-if="comic.images && comic.images.length > 0" :src="comic.images[0].path + '.' + comic.images[0].extension" alt="Comic Cover" />
      <div v-else class="blank-comic-card">
        <p class="image-not-available">Image not available</p>
      </div>
    </div>
    <div class="comic-details">
      <h2>{{ comic.title }}</h2>
      <p v-if="comic.description">{{ comic.description }}</p>
      <p>Issue Number: {{ comic.issueNumber }}</p>
      <p>Page Count: {{ comic.pageCount }}</p>
    </div>
  </div>
</div>
</template>

<script>
export default {
  props: {
    collection: Array,
  },
};
</script>