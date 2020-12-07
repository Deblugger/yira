<template>
  <div>
    <b-row class="mb-5">
      <b-col><h2>{{this.projectData.name}}</h2></b-col>
      <b-col><b-button class="float-right" variant="primary" :to="'/new-project/' + this.projectId">Edit</b-button></b-col>
    </b-row>
   
    <b-row style="border-right: 1px solid rgba(0,0,0,0.12);">
      <b-col v-for="projectColumn in projectColumns" class="col-sm project-column" :key="projectColumn">
        {{projectColumn}}
      </b-col>
    </b-row>
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