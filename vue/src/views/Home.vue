<template>
  <div class="home">
    <h1 class="comic-heading">Comic Book Search</h1>
    <div class="search-container">
      <input v-model="searchQuery" placeholder="Enter comic name" class="search-input" @keydown.enter="searchComics" />
      <button @click="searchComics" class="search-button">Search</button>
    </div>
    <div class="search-results">
      <div v-for="comic in comics" :key="comic.id" class="comic-card"  @click="handleCardClick(comic)" >
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
          <button @click="addToCollection(comic)" class="add-to-collection-button">Add to Collection</button>
          <Collection :collection="collection" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import comicService from "../services/ComicService.js";
import { gsap } from "gsap";
import Collection from "../views/Collection.vue";

export default {
  name: "home",
  data() {
    return {
      message: "",
      searchQuery: "",
      comics: [], // This array will hold all comics fetched initially and after searching
      collection: []
    };
  },
  components: {
    Collection,
  },
  created() {
    // Fetch and display all available comics initially
    this.fetchAllComics();
  },
  methods: {
    fetchAllComics() {
      comicService.getAllComics().then((response) => {
        this.comics = response.data.data.results;
      });
    },
    searchComics() {
      comicService.search(this.searchQuery).then((response) => {
        this.comics = response.data.data.results;
      });
    },
    addToCollection(comic) {
      this.collection.push(comic);
      this.message = `${comic.title} has been added to your collection.`;
    },
    handleCardClick(comic) {
      const cardElement = document.querySelector(`#comic-${comic.id}`);

      gsap.to(cardElement, {
        duration: 1,
        ease: "power3.inOut",
        scaleX: 0.2,
        scaleY: 0.2,
        opacity: 0,
        onComplete: () => {
          this.addToCollection(comic); // Add the comic to the collection
          gsap.to(cardElement, {
            duration: 0.5,
            scaleX: 1,
            scaleY: 1,
            opacity: 1,
            onComplete: () => {
              // Add any additional logic you need here
              console.log("Animation complete");
            }
          }); // Restore the card's appearance
        }
      });
    }
  }
};
</script>

<style>

.home {
  text-align: center;
}

.comic-heading {
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

.comic-card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  display: inline-block;
  max-width: 200px;
  max-height: 300px; /* Adjust the max-height to your preference */
  overflow: hidden; /* Ensure content doesn't overflow */
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* Add transition */
}
.comic-card:hover {
  transform: translateY(-10px) scale(1.05);
  animation: rainbow-border 2s infinite linear;
}
.comic-card.clicked {
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

.comic-card img {
  max-width: 100%;
  height: auto;
}
.home {
  /* Set the background gradient */
  background: linear-gradient(45deg, pink 50%, lightblue 50%);
  padding: 20px;
  color: white;
  position: relative;
}
.add-to-collection-button {
  margin-top: 10px;
  padding: 8px 16px;
  font-size: 14px;
  background-color: crimson; /* Set background color to crimson */
  color: white; /* Set text color to white */
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  animation: breathing 4s infinite;
}

.add-to-collection-button:hover {
  background-color: #9C0017; /* Darker crimson on hover */
  animation: none;
}
@keyframes breathing {
   0% { background-color: #FFD700; } /* Gold color */
  50% { background-color: #FFB400; } /* Darker gold color */
  100% { background-color: #FFD700; } /* Return to gold color */

}
.blank-comic-card {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
  color: red;
  font-weight: bold;
  height: 200px;
}
</style>