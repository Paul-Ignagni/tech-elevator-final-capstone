<template>
  <div>
    <h1>Create a Collection</h1>
      <form>
        </form>
    </div>
</template>

<script>
import axios from "axios";

export default {
    methods: {
        async createCollection() {
      try {
        const response = await axios.post("/collections/addCollection", {
          userId: this.userId, // Ensure this.userId is set correctly
          name: "New Collection Name",
        });
        return response.data;
      } catch (error) {
        throw new Error("Failed to create collection");
      }
    },
    async addComicsToCollection(collectionId) {
      try {
        for (const comic of this.collection) {
          const comicId = comic.id; // Ensure comic.id exists in the comic object
          await axios.post(`/collections/${collectionId}/addComic/${comicId}`);
        }
      } catch (error) {
        throw new Error("Failed to add comics to collection");
      }
    },
    createNewCollection() {
      this.$emit("create-new-collection");
    },
    async saveCollection() {
      try {
        const newCollection = await this.createCollection(); // Create a new collection
        if (newCollection) {
          await this.addComicsToCollection(newCollection.id); // Add comics to the new collection
          console.log("Collection saved successfully");
        }
      } catch (error) {
        console.error("Failed to save collection:", error);
      }
    },
    }
}
</script>

<style>

</style>