<template>
  <div id="app">
    <FeedbackList />
    <SendFeedback />
  </div>
</template>

<script>
import FeedbackList from './components/FeedbackList.vue';
import SendFeedback from './components/SendFeedback.vue';


export default {

  name: 'App',
  components: {
    FeedbackList,
    SendFeedback
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
