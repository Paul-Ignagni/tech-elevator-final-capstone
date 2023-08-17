import axios from 'axios';

export default {
  // getTotalComicsByCharacter(characterName) {
  //   return axios.get(`${API_BASE_URL}/comics/totalByCharacter`, {
  //     params: {
  //       characterName: characterName
  //     }
  //   });
  // },

  // getTotalComicsByCreator(creatorName) {
  //   return axios.get(`${API_BASE_URL}/comics/totalByCreator`, {
  //     params: {
  //       creatorName: creatorName
  //     }
  //   });
  // },
    getAllComics() {
        console.log("Fetching all comics");
        return axios.get('/all'); // Adjust the endpoint URL based on your backend configuration
      },

  searchComic(title) {
      console.log("here in my service")
    return axios.get('/search/' + title)
  },

  searchCharacter(name) {
    return axios.get('/characters/search/' + name)
  },

  searchCreator(name) {
    return axios.get('/creators/search/' + name)
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
  },

  getAllCharacters() {
    return axios.get('/characters')
  },

  getCharacterInfo(serial) {
    return axios.get('/characters/' + serial)
  },

  getAllCreators() {
    return axios.get('/creators')
  },

  getCreatorInfo(serial) {
    return axios.get('/creators/' + serial)
  }

}
