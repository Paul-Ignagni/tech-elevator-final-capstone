import axios from 'axios';

export default {
    getAllComics() {
        console.log("Fetching all comics");
        return axios.get('/all'); // Adjust the endpoint URL based on your backend configuration
      },

  search(title) {
      console.log("here in my service")
    return axios.get('/search/' + title)
  },

  getPublicCollections() {
    console.log('Retrieving public collections')
    return axios.get('/collections')
  },

  getComicsInCollections(collectionId) {
    console.log('Retrieving comics in collection')
    return axios.get('/collections/' + collectionId)
  },

  getUserCollections(userId) {
    console.log('Retrieving user collections')
    return axios.get('/collections/myCollection/' + userId)
  },

  getComicInfo(serial) {
    console.log('Retrieving comic info')
    return axios.get('/comic/' + serial)
  }

}
