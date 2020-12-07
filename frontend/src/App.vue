<template>
  <div id="app">
    <div>
      <b-navbar type="dark" variant="dark">
        <b-navbar-brand to="/">Yira</b-navbar-brand>
        <b-navbar-nav>
          <b-nav-item to="/">Home</b-nav-item>
          <b-nav-item to="/about" style="border-right: 1px grey solid;">About</b-nav-item>
          <b-nav-item v-for="item in projects" :key="item.id" :to="'/project/'+item.id">{{item.name}}</b-nav-item>          
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
            <b-button size="md" class="my-2 my-sm-0" to="/new-project">New project</b-button>
          </b-navbar-nav>
      </b-navbar>
      <router-view style="padding: 25px;" :key="$route.fullPath" v-on:project-created="onProjectCreated" v-on:project-updated="onProjectUpdated" v-on:project-deleted="onProjectDeleted"/>
    </div>
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
      "projects": [],
      "showDialog": false,
      "selectedProjectId": 0
    }
  },
  mounted() {
    axios.get("http://localhost:8080/projects").then(res => this.projects = res.data)
  },
  methods: {
    onProjectCreated(createdProject) {
      this.projects.push(createdProject);
    },
    onProjectUpdated(updtaedProject) {
      this.projects.forEach(project => {
        if(project.id == updtaedProject.id) {
          project.name = updtaedProject.name;
        }
      })
    },
    onProjectDeleted(projectId) {
      this.projects.forEach((project, index) => {
        if(project.id == projectId) {
          this.projects.splice(index, 1);
        }
      })
    }
  },
}
</script>
