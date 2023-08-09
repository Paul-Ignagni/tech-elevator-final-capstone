import axios from 'axios';

export default {
    getAllComics() {
        console.log("Fetching all comics");
        return axios.get('/search/all'); // Adjust the endpoint URL based on your backend configuration
      },

  search(title) {
      console.log("here in my service")
    return axios.get('/search/' + title)
  },

}
