<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
  </div>
</template>

<script>
import comicService from "../services/ComicService.js"
export default {
  name: "home",
  created(){
    comicService.search("venom").then ((response) => {
      console.log(response)
      console.log(response.data.data.results[0].images[0])
    })

  }
};
</script>
