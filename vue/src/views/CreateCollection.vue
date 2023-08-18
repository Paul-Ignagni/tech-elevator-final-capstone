<template>
  <form v-on:submit.prevent="createCollection" class="collection-form">
    <h1>Create a Collection</h1>
    <div class="form-group">
      <label for="name">Collection Name </label>
      <input id="name" type="text" class="form-control" v-model="collection.name" autocomplete="off" />
    </div>
    <div class="form-group">
      <label for="isPublic">Make collection public? </label>
      <input type="checkbox" name="isPublic" id="isPublic" v-model="collection.isPublic" />
    </div>
    <div>
    <button class="btn btn-submit">Create</button>
    <button class="btn btn-cancel" v-on:click="clearForm" type="button">Clear</button>
    </div>
  </form>
</template>

<script>
// import axios from "axios";
import ComicService from '../services/ComicService';

export default {
  data() {
    return {
      collection: {
        userId: 0,
        name: "",
        isPublic: false
      },
      newCollection: {

      }
    }
  },
    methods: {
      createCollection() {
        const newCollection = {
          userId: 1,
          name: this.collection.name,
          isPublic: this.collection.isPublic
        };
        ComicService.createCollection(newCollection).then(response => {
          if (response.status === 201) {
            alert("Successfully created new collection")
          }
        })
        .catch(error => {
          alert(error)
        });
        this.clearForm();
      },
      clearForm() {
        this.collection.name = "",
        this.collection.isPublic = false
      },
    //     async createCollection() {
    //   try {
    //     const response = await axios.post("/collections/addCollection", {
    //       userId: this.userId, // Ensure this.userId is set correctly
    //       name: "New Collection Name",
    //     });
    //     return response.data;
    //   } catch (error) {
    //     throw new Error("Failed to create collection");
    //   }
    // },
    // async addComicsToCollection(collectionId) {
    //   try {
    //     for (const comic of this.collection) {
    //       const comicId = comic.id; // Ensure comic.id exists in the comic object
    //       await axios.post(`/collections/${collectionId}/addComic/${comicId}`);
    //     }
    //   } catch (error) {
    //     throw new Error("Failed to add comics to collection");
    //   }
    // },
    // createNewCollection() {
    //   this.$emit("create-new-collection");
    // },
    // async saveCollection() {
    //   try {
    //     const newCollection = await this.createCollection(); // Create a new collection
    //     if (newCollection) {
    //       await this.addComicsToCollection(newCollection.id); // Add comics to the new collection
    //       console.log("Collection saved successfully");
    //     }
    //   } catch (error) {
    //     console.error("Failed to save collection:", error);
    //   }
    // },
    }
}
</script>

<style>
.collection-form{
  text-align: center;
}
.form-group {
  font-size: 64px;
}
</style>