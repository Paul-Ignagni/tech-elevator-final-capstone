<template>
  <div class="collections">
    <div class="sidebar" :class="{ open: isSidebarOpen }"
      @mouseenter="openSidebar"
      @mouseleave="closeSidebar"
    >
      <nav class="sidebar-nav">
        <h2>Navigation Menu</h2>
        <ul>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/login">Login</router-link></li>
          <li><router-link to="/profile">Profile</router-link></li>
        </ul>
      </nav>
    </div>
    <h2>Public Collections</h2>

    <!-- Search bars for character and creator -->
    <div class="search-bar">
      <label for="characterSearch">Search by Character:</label>
      <input v-model="characterSearchKeyword" id="characterSearch" placeholder="Enter character name">
      <button @click="searchComicsByCharacter">Search</button>
      <p v-if="characterKeywordComicCount !== null">
        Total comics with character "{{ characterSearchKeyword }}": {{ characterKeywordComicCount }}
      </p>

      <label for="creatorSearch">Search by Creator:</label>
      <input v-model="creatorSearchKeyword" id="creatorSearch" placeholder="Enter creator name">
      <button @click="searchComicsByCreator">Search</button>
      <p v-if="creatorKeywordComicCount !== null">
        Total comics with creator "{{ creatorSearchKeyword }}": {{ creatorKeywordComicCount }}
      </p>
    </div>

    <!-- Button to calculate total comics -->
    <div>
    <button @click="calculateTotalAllComics">How many comics are there in all the collections?</button>
    <p v-if="totalAllComics !== null">
      Total comics in all collections: {{ totalAllComics }}
    </p>
    </div>

    <div
      v-for="collection in collections" :key="collection.id" class="collection-comic">
      <h3>{{ collection.name }}</h3>
      <h5>User ID: {{ collection.userId }}</h5>
      <button type="button">View this collection</button>
    </div>
  </div>
</template>

<script>
import ComicService from "../services/ComicService";

export default {
  data() {
    return {
      collections: [],
      characterSearchKeyword: "",
      characterKeywordComicCount: null,
      creatorSearchKeyword: "",
      creatorKeywordComicCount: null,
      totalAllComics: null,
    };
  },

  created() {
    // Fetch and display all available collections initially
    this.fetchAllCollections();
  },

  methods: {
    fetchAllCollections() {
      ComicService.getPublicCollections().then(response => {
        this.collections = response.data;
      });
    },

     searchComicsByCharacter() {
      ComicService.getTotalComicsByCharacter(this.characterSearchKeyword).then(response => {
        this.characterKeywordComicCount = response.data;
      }).catch(error => {
        console.error("Error searching for comics:", error);
      });
    },

    searchComicsByCreator() {
      ComicService.getTotalComicsByCreator(this.creatorSearchKeyword).then(response => {
        this.creatorKeywordComicCount = response.data;
      }).catch(error => {
        console.error("Error searching for comics:", error);
      });
    },

    calculateTotalAllComics() {
      ComicService.getTotalComicsInCollections().then((response) => {
        this.totalAllComics = response.data;
      })
    }
  }
};
</script>

<style>

.collections {
  text-align: center;
  background: url(@/components/PurpleBackground.jpg);
  height: 100vh;
}

h2 {
  font-size: 30px;
  padding: 10px;
  font-family: "Comic Sans MS", cursive;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
}

label {
  padding: 10px;
  font-family: "Comic Sans MS", cursive;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
}

button {
  font-family: "Comic Sans MS", cursive;
  text-shadow: 2px 2px #000000;
  font-size: 24px;
  background-color: #9616ff;
  color: #ffffff;
  border-style: none;
  border-radius: 5px;
  margin-left: 1rem;
  transition: background-color 0.3s ease-in-out;
  padding: 5px;
}

button:hover {
  background-color: #6f0fbd;
}

.search-bar {
  padding: 5px;
  margin-bottom: 1rem;
}

.sidebar {
  position: fixed;
  background: url(@/components/GrayBackground.jpg);
  background-size: cover;
  background-position: center;
  border-radius: 0px 25px 25px 0px;
  border-style: solid;
  border-color: #000000;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  top: 20%;
  left: 0;
  left: -250px;
  width: 250px;
  height: 250px;
  transition: left 0.3s ease-in-out;
  z-index: 1000;
}

.sidebar.open {
  left: 0;
}

.sidebar h2 {
  text-align: center;
  padding: 0px;
  margin: 10px;
  color: #9616ff;
  text-shadow: 2px 2px #000000;
  background: #ffffff;
  font-family: "Comic Sans MS", cursive;
  font-weight: bold;
  border-style: outset;
  border-radius: 5px;
}

.sidebar-nav {
  padding: 0px;
}

.sidebar-nav ul {
  list-style: none;
  padding: 20;
  margin: 20;
}

.sidebar-nav li {
  margin-bottom: 10px;
}

.sidebar-nav a {
  font-family: "Comic Sans MS", cursive;
  color: #ffffff;
  text-decoration: none;
  text-shadow: 2px 2px #000000;
  font-weight: 300;
  font-size: 24px;
  transition: font-size 0.3s, margin-bottom 0.3s;
}

.sidebar-nav a:hover {
  font-weight: 500;
  font-size: 36px;
  margin-bottom: 20px;
}
</style>