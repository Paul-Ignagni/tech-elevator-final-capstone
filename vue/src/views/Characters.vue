<template>
  <div class="characters">
    <h1 class="character-heading">Character Search</h1>
    <div class="search-container">
      <input v-model="searchQuery" placeholder="Enter character name" class="search-input" @keydown.enter="searchCharacter" />
      <button @click="searchCharacter" class="search-button">Search</button>
    </div>
    <div class="search-results">
      <div v-for="character in characters" :key="character.characterSerial" class="character-card"  @click="handleCardClick(character.characterSerial)" >
        <div class="character-image">
          <img v-if="character.characterImage && character.characterImage.length > 0" :src="character.characterImage" alt="Character Image" />
         <div v-else class="blank-character-card">
            <p class="image-not-available">Image not available</p>
          </div>
        </div>
        <div class="character-details">
          <p>{{ character.characterName }}</p>
        </div>
      </div>
      <div class="sidebar" :class="{ 'open': isSidebarOpen }" @mouseenter="openSidebar" @mouseleave="closeSidebar">
        <!--Add additional stuff here once figure out what is needed (and if the damn thing works)-->
        </div>
    </div>
    
  </div>
</template>


<script>
import comicService from "../services/ComicService.js";


export default {
  name: "home",
  data() {
    return {
      searchQuery: "",
      characters: [],
      userId: 0, // Define the userId
      isSidebarOpen: false,
    };
  },

  
  created() {
    this.fetchAllCharacters();
  },
  methods: {
    fetchAllCharacters() {
      comicService.getAllCharacters().then((response) => {
        this.characters = response.data;
      });
    },
    searchCharacter() {
      comicService.searchCharacter(this.searchQuery).then((response) => {
        this.characters = response.data;
      });
    },
    handleCardClick(id) {
      this.$router.push({ name: 'Character', params: { serial: id} })
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


.home {
  text-align: center;
}

.character-heading {
    text-align: center;
  font-family: 'Comic Sans MS', cursive; /* Use a comic book font here */
  font-size: 28px;
  margin-bottom: 20px;
  color: darkcyan;
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
  border: 2px solid #000; 
  border-radius: 10px;
}

.search-button {
  padding: 8px 16px;
  font-size: 16px;
  background-color: crimson; 
  color: #FFF; 
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.search-button:hover {
  background-color: #990000; 
}

.character-card {
  position: relative;
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  display: inline-block;
  max-width: 200px;
  max-height: 300px; /* Adjust the max-height to your preference */
  overflow: hidden; /* Ensure content doesn't overflow */
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* Add transition */
}
.character-card:hover {
  transform: translateY(-10px) scale(1.05);
  animation: rainbow-border 2s infinite linear;
}
.character-card.clicked {
  transform: translateY(-5px) scale(0.9);
  opacity: 0;
  box-shadow: none; /* Remove box shadow during the disappearing animation */
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

.character-card img {
  max-width: 100%;
  height: auto;
}
.characters {
  /* Set the background gradient */
  background: linear-gradient(45deg, pink 50%, lightblue 50%);
  padding: 20px;
  color: white;
  position: relative;
}
.add-to-collection-button {
  position: absolute; /* Position the button absolutely within the comic card */
  bottom: 10px; /* Adjust the distance from the bottom as needed */
  left: 50%; /* Position the button horizontally centered */
  transform: translateX(-50%); /* Center the button horizontally */
  padding: 8px 16px;
  font-size: 14px;
  background-color: crimson;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  animation: breathing 4s infinite;
}
.sidebar {
  position: fixed;
  top: 0;
  left: -300px; /* Position when off-screen. Because, yeah, we don't wanna see them unless they're wanted lol*/
  width: 300px;
  height: 100%;
  background-color: lightblue;
  color: white;
  transition: left 0.3s ease-in-out;
  z-index: 1000; /* This is will bring it to the front so that no other elements on the webpage show in front of the sidebar.*/
}
.sidebar.open {
  left: 0; /* No idea what I wanna add here until I view it working in full (aka when I can actually view the damn page properly)*/
}
</style>

