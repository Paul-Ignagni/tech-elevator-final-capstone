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
    <button @click="createNewCollection" class="create-collection-button">Create New Collection</button>
    <div v-if="createdCollection">
      <h3>New Collection Created:</h3>
      <p>Collection ID: {{ createdCollection.id }}</p>
      <p>Name: {{ createdCollection.name }}</p>
    </div>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  props: {
    collection: Array,
    createdCollection: Object,
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
        const response = await axios.post('/collections/addCollection', {
          userId: this.userId, // Ensure this.userId is set correctly
          name: 'New Collection Name',
        });
        return response.data;
      } catch (error) {
        throw new Error('Failed to create collection');
      }
    },
    async addComicsToCollection(collectionId) {
      try {
        for (const comic of this.collection) {
          const comicId = comic.id; // Ensure comic.id exists in the comic object
          await axios.post(`/collections/${collectionId}/addComic/${comicId}`);
        }
      } catch (error) {
        throw new Error('Failed to add comics to collection');
      }
    },
    createNewCollection() {
      this.$emit('create-new-collection');
    },
  },
};
</script>

<style>
/* Your styles here */
</style>
