<template>
  <div class="collection">
    <h2>Your Collection</h2>
    <div v-for="comic in collection" :key="comic.id" class="collection-comic">
      <div class="comic-cover">
        <img
          v-if="comic.images && comic.images.length > 0"
          :src="comic.images[0].path + '.' + comic.images[0].extension"
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
        <p>Page Count: {{ comic.pageCount }}</p>
      </div>
    </div>
    <button @click="saveCollection" class="save-collection-button">Save Collection</button>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  props: {
    collection: Array,
  },
  methods: {
    async saveCollection() {
      try {
        const newCollection = await this.createCollection(); // Create a new collection
        if (newCollection) {
          await this.addComicsToCollection(newCollection.id); // Add comics to the new collection
          console.log('Collection saved successfully');
        }
      } catch (error) {
        console.error('Failed to save collection:', error);
      }
    },
    async createCollection() {
      try {
        const response = await axios.post('/api/collections/addCollection', {
          userId: this.userId,
          name: 'New Collection Name', // Set the collection name here
        });
        return response.data;
      } catch (error) {
        throw new Error('Failed to create collection');
      }
    },
    async addComicsToCollection(collectionId) {
      try {
        for (const comic of this.collection) {
          const comicId = comic.id; // Get the comic's ID
          await axios.post(`/api/collections/${collectionId}/addComic/${comicId}`);
        }
      } catch (error) {
        throw new Error('Failed to add comics to collection');
      }
    },
  },
};
</script>

<style>
/* Add your styles here */
</style>
