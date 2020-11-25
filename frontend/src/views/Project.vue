<template>
  <div>
    <h2>{{this.projectData.name}}</h2>
    <div class="md-layout md-gutter">
      <div v-for="projectColumn in projectColumns" class="md-layout-item project-column" :key="projectColumn">
        {{projectColumn}}
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {  
  name: 'Project',
  data() {
    return {
      "projectId": this.$route.params.id,
      "projectData": {},
      "projectColumns": []
    }
  },
  mounted() {
    axios.get("http://localhost:8080/projects/" + this.projectId).then(res => {
      this.projectData = res.data
      this.projectColumns = res.data.states
    })
  }
}
</script>

<style>

  .project-column {
    border-left: 1px solid rgba(0,0,0,0.12);
    min-height: 90vh
  }
</style>