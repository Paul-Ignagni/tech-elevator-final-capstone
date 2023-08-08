import axios from 'axios';

export default {

  search(title) {
      console.log("here in my service")
    return axios.get('/search/' + title)
  },

}
