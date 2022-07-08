import { createStore } from 'vuex'

export default createStore({
  state: {
    newsTotal: ''
  },
  mutations: {
    setNewsTotal (state, total) {
      state.newsTotal = total
    }
  },
  actions: {
  },
  modules: {
  }
})
