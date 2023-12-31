<template>
  <div class="home">
    
    <h1 class="comic-heading">Comic Book Search</h1>
    <div class="search-container">
      <input v-model="searchQuery" placeholder="Enter comic search" class="search-input" @keydown.enter="searchComics" />
      <button @click="searchComics" class="search-button">Search</button>
    </div>
    <div class="search-results">
      <div v-for="comic in comics" :key="comic.id" class="comic-card"  @click="handleCardClick(comic.serial)" >
        <div class="comic-cover">
          <img v-if="comic.images && comic.images.length > 0" :src="comic.images" alt="Comic Cover" />
         <div v-else class="blank-comic-card">
            <p class="image-not-available">Image not available</p>
          </div>
        </div>
        <div class="comic-details">
          <p>{{ comic.title }}</p>
        </div>
      </div>
    <div
      class="sidebar"
      :class="{ open: isSidebarOpen }"
      @mouseenter="openSidebar"
      @mouseleave="closeSidebar"
    >
      <nav class="sidebar-nav">
        <h2>Navigation Menu</h2>
        <ul>
          <li><router-link to="/login">Login</router-link></li>
          <li><router-link to="/">Home</router-link></li>
          <li><router-link to="/collections">Collections</router-link></li>
          <li><router-link to="/collections/collection/create">Create a Collection</router-link></li>
          <li><router-link to="/characters">Characters</router-link></li>
          <li><router-link to="/creators">Creators</router-link></li>
          <li><router-link to="/logout">Logout</router-link></li>
        </ul>
      </nav>
        </div>
    </div>
    
  </div>
</template>


<script>
import comicService from "../services/ComicService.js";
import axios from "axios"; // Import axios once

export default {
  name: "home",
  data() {
    return {
      searchQuery: "",
      comics: [], // This array will hold all comics fetched initially and after searching
      collection: [],
      userId: 0, // Define the userId
      initialCollectionId: 0, // Define the initialCollectionId
      isSidebarOpen: false,
      comicId: 0,
    };
  },

  
  created() {
    // Fetch and display all available comics initially
    this.fetchAllComics();
  },
  methods: {
    fetchAllComics() {
      comicService.getAllComics().then((response) => {
        this.comics = response.data;
      });
    },
    searchComics() {
      comicService.searchComic(this.searchQuery).then((response) => {
        this.comics = response.data;
      });
    },
    handleCardClick(id) {
      this.$router.push({ name: 'Comic', params: { comicId: id} })
    },
    async addToCollectionAndNavigate(comic) {
      try {
        const response = await axios.post(`/collections/${this.initialCollectionId}/addComic/${comic.id}`);
        if (response.status === 200) {
          this.$store.commit('addToCollection', comic);
          console.log('Comic added to collection successfully');
          this.$router.push({name: "collection"});
        }
      } catch (error) {
        console.error('Failed to add comic to collection:', error);
      }
    },
    openSidebar() {
      this.isSidebarOpen = true;
    },
    closeSidebar() {
      this.isSidebarOpen = false;
    },
    },
  };

</script>

<style>

.comic-details {
  font-size: 24px;
}

.comic-heading {
  font-family: 'Comic Sans MS', cursive;
  font-size: 28px;
  margin-bottom: 20px;
  color: #ffffff;
  text-shadow: 2px 2px #000000;
}

.search-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.search-input {
  padding: 8px;
  font-size: 16px;
  border: 2px solid #000000; 
  border-radius: 10px;
}

button.search-button {
  padding: 8px 16px;
  font-size: 20px;
  font-family: "Comic Sans MS", cursive;
  text-shadow: 2px 2px #000000;
  background-color: #9616ff; 
  color: #ffffff; 
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

button.search-button:hover {
  background-color: #7d11d4; 
}

.comic-card {
  position: relative;
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  display: inline-block;
  max-width: 200px;
  max-height: 300px; 
  overflow: hidden; 
  transition: transform 0.3s ease, box-shadow 0.3s ease; 
}
.comic-card:hover {
  transform: translateY(-10px) scale(1.05);
  animation: rainbow-border 2s infinite linear;
}
.comic-card.clicked {
  transform: translateY(-5px) scale(0.9);
  opacity: 0;
  box-shadow: none;
}
@keyframes rainbow-border {
  0% {
    box-shadow: 0 0 10px #00eeff, 0 0 20px #f700fffa, 0 0 30px #c8ff00, 0 0 40px #ffbf00, 0 0 50px #ffbb00,
      0 0 60px #ffd000, 0 0 70px #00f7ff, 0 0 80px #40ff00, 0 0 90px #00ff00;
  }
  100% {
    box-shadow: 0 0 10px #ff0000, 0 0 20px #ff4000, 0 0 30px #ff8000, 0 0 40px #ffbf00, 0 0 50px #ffff00,
      0 0 60px #bfff00, 0 0 70px #ea00ff63, 0 0 80px #f302f3, 0 0 90px #00ff00;
  }
}

.comic-card img {
  max-width: 100%;
  height: auto;
}

.home {
  text-align: center; 
  background: url(@/components/PurpleBackground.jpg);
  padding: 20px;
  color: white;
  position: relative;
  background-size: cover;
  background-position: centered;
}

/* .add-to-collection-button {
  position: absolute; 
  bottom: 10px; 
  left: 50%; 
  transform: translateX(-50%);
  padding: 8px 16px;
  font-size: 14px;
  background-color: crimson;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  animation: breathing 4s infinite;
} */

div.sidebar {
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
  height: 500px;
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
