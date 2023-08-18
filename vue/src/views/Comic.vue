<template>
  <div class="comic">
    <div :key="comic.serial" class="comic-cover">
      <img
        v-if="comic.images && comic.images.length > 0"
        :src="comic.images"
        alt="Comic Cover"
      />
      <div v-else class="blank-comic-card">
        <p class="image-not-available">Image not available</p>
      </div>
    </div>
    <div class="comic-details">
      <h2>{{ comic.title }}</h2>
      <p v-if="comic.description">{{ comic.description }}</p>
      <p v-if="comic.series">Series: {{comic.series}}</p>
      <p>Issue Number: {{ comic.issueNumber }}</p>
      <p v-if="comic.pageCount">Page Count: {{ comic.pageCount }}</p>
      <h3 v-if="authors.length > 0">Authors:</h3>
      <div v-for="author in authors" :key="author">
        {{ author }}
      </div>
      <h3 v-if="characters.length > 0">Characters:</h3>
      <div v-for="character in characters" :key="character">
        {{ character }}
      </div>
    </div>
    
    <div v-if="collections.length > 0" class="button-add">
      <h3>Add to a collection:</h3>
      <label for="collection">Select a collection: </label>
      <select id="collection" class="collection-tags" v-model="collectionId">
        <option v-for="collection in collections" :key="collection.id" :value="collection.id">
          {{ collection.name }}
        </option>
      </select>
    </div>
    <div class="add-to-collection-button-container">
      <button
        v-if="collections.length > 0"
        @click="addToCollectionAndNavigate()"
        class="add-to-collection-button"
      >
        Add to Collection
      </button>
    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      comic: [],
      authors: [],
      characters: [],
      collections: [],
      userId: 1,
      comicId: this.$route.params.comicId,
      collectionId: 0,
      // comicEntry: {
      //   collectionId: 0,
      //   comicId: this.comicId
      // },
    };
  },
  created() {
    this.fetchComicInfo(this.comicId);
    this.fetchAuthors(this.comicId);
    this.fetchCharacters(this.comicId);
    this.fetchCollections(this.userId);
  },
  methods: {
    fetchComicInfo(serial) {
      ComicService.getComicInfo(serial).then((response) => {
        this.comic = response.data;
      });
    },
    fetchAuthors(serial) {
      ComicService.getAuthorsForComic(serial).then((response) => {
        this.authors = response.data;
      });
    },
    fetchCharacters(serial) {
      ComicService.getCharactersForComic(serial).then((response) => {
        this.characters = response.data;
      })
    },
    fetchCollections(userId) {
      ComicService.getUserCollections(userId).then((response) => {
        this.collections = response.data;
      })
    },
    addToCollectionAndNavigate() {
      if (this.collectionId !== 0) {
      const newEntry = {
        collectionId: this.collectionId,
        comicId: parseInt(this.comicId),
      };
      console.log(newEntry);
      ComicService.addComicToCollection(newEntry).then(response => {
          if (response.status === 201) {
            alert("Successfully created new collection")
          }
        })
        .catch(error => {
          alert(error)
        });
      }
    }
  },
};
</script>

<style>
.comic {
  text-align: center;
  font-size: 24px;
}
.add-to-collection-button-container {
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  width: 100%;
  position: fixed;
  bottom: 0px; /* Adjust this value as needed */
  left: 0;
  z-index: 999;
  background-color: #fff;
  padding: 10px 0;
  box-shadow: 0px -2px 10px rgba(0, 0, 0, 0.1);
}
.add-to-collection-button {
  background-color: #007BFF;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}
.button-add {
  padding-bottom: 100px;
}
</style>