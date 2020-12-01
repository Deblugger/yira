<template>
  <div id="app">
    <md-app md-waterfall md-mode="fixed">
      <md-app-toolbar class="md-primary">
        <span class="md-title">Yira</span>
      </md-app-toolbar>
      <md-app-drawer md-permanent="full">
        <md-toolbar class="md-transparent" md-elevation="0">
          Menu
        </md-toolbar>
        <md-list>
          <md-list-item>
            <router-link to="/">Home</router-link>
          </md-list-item>
          <md-list-item style="border-bottom: 1px solid rgba(0,0,0,0.12)">
            <router-link to="/about">About</router-link>
          </md-list-item>
          <md-list-item v-for="item in projects" :key="item.id">
            <router-link :to="'/project/'+item.id">{{item.name}}</router-link>
          </md-list-item>
        </md-list>
        <md-button to="/new-project" class="md-raised md-primary"><md-icon>add</md-icon> New project</md-button>
      </md-app-drawer>
      <md-app-content>
        <router-view :key="$route.fullPath"/>
      </md-app-content>
    </md-app>    
  </div>
</template>

<style>
.md-app {
  max-height: 400px;
  border: 1px solid rgba(#000, .12);
  min-height: 100vh;
}
</style>

<script>
import axios from 'axios'

export default {
  name: "App",
  data() {
    return {
      "projects": []
    }
  },
  mounted() {
    axios.get("http://localhost:8080/projects").then(res => this.projects = res.data)
  },
}
</script>
