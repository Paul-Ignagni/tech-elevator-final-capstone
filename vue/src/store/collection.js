const state = {
    collection: [] 
  };
  
  const mutations = {
    ADD_TO_COLLECTION(state, comic) {
      state.collection.push(comic);
    },
  };
  
  const actions = {
    addToCollection({ commit }, comic) {
      commit('ADD_TO_COLLECTION', comic);
    },
  };
  
  const getters = {
    getCollection: state => state.collection,
  };
  
  export default {
    state,
    mutations,
    actions,
    getters
  };