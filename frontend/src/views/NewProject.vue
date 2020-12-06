<template>
  <div>
    <form novalidate class="md-layout">
      <md-button class="md-primary" v-if="this.projectId === undefined" @click="saveProject" :disabled="sending">Create project</md-button>
      <md-button class="md-primary" v-if="this.projectId !== undefined" @click="updateProject" :disabled="sending">Update project</md-button>
      <md-button class="md-accent" v-if="this.projectId !== undefined" @click="showDialog = true">Remove</md-button>
      <md-field>
        <label for="project-name">Project Name</label>
        <md-input name="project-name" id="project-name" v-model="projectName" :disabled="sending" />
      </md-field>
      <md-field v-if="this.projectId === undefined">
        <label for="state-name">New state</label>
        <md-input name="state-name" id="state-name" v-model="newState" :disabled="sending" />
        <md-button @click="addNewState()" class="md-primary md-raised">Add</md-button>
      </md-field>

      <draggable v-if="this.projectId === undefined" v-model="states" @start="drag=true" @change="moveElement" @end="drag=false">
        <md-card v-for="element in states" :key="element.name">
          <md-card-content>
            {{element.name}}
            <md-badge class="md-primary md-square" :md-content="element.position" />
          </md-card-content>
          <md-card-actions>
            <md-button @click="removeState(element)">Remove</md-button>
          </md-card-actions>
        </md-card>
      </draggable>

      <md-progress-bar md-mode="indeterminate" v-if="sending" />

      <md-snackbar :md-active.sync="savedSuccesfully">Project created successfully!</md-snackbar>
    </form>

    <!-- Delete Project Dialog -->
    <md-dialog v-if="this.projectId !== undefined" :md-active.sync="showDialog">
        <md-dialog-title>Hey!</md-dialog-title>

        You're going to delete a whole project, including tasks. Are you sure?

        <md-dialog-actions>
            <md-button class="md-primary" @click="showDialog = false">No</md-button>
            <md-button class="md-accent md-raised" @click="deleteProject">Yes</md-button>
        </md-dialog-actions>
    </md-dialog>    
  </div>
</template>

<script>
import axios from 'axios'
import draggable from 'vuedraggable'

export default {  
  name: 'NewProject',
  components: {
    draggable
  },
  data() {
    return {
      "sending": false,
      "savedSuccesfully": false,
      "projectName": "",
      "owner": 1, // TODO: Change by real USER ID
      "states": [],
      "newState": "",
      "lastPosition": 0,
      "projectId": this.$route.params.id,
      "showDialog": false
    }
  },
  mounted() {
    if(this.projectId !== undefined) {
      axios.get("http://localhost:8080/projects/" + this.projectId).then(res => {
        this.projectName = res.data.name;
      })
    }
  },
  methods: {
    addNewState() {
      this.states.push({
        "name": this.newState,
        "position": this.lastPosition
      })
      this.newState = ""
      this.lastPosition++
    },
    saveProject() {
      if(this.states.length == 0) {
        alert("Sorry, but you have to create at least one state")
      } else {
        axios.post("http://localhost:8080/projects", {
          "name": this.projectName,
          "owner": this.owner,
          "states": this.states
        }).then(res => {
          if (res.status == 201) {
            this.$emit('project-created', res.data)
            this.$router.push("/project/" + res.data.id)
          }
        })
      }
    },
    updateProject() {
      axios.put("http://localhost:8080/projects/" + this.projectId, {
        "name": this.projectName
      }).then(res => {
        if (res.status == 200) {
          this.$emit('project-updated', res.data)
          this.$router.push("/project/" + res.data.id)
        }
      })
    },    
    deleteProject() {
      axios.delete("http://localhost:8080/projects/" + this.projectId).then((res) => {
        if(res.status == 204) {
          this.showDialog = false;
          this.$emit('project-deleted', this.projectId)
          this.$router.push("/")
        }
        
      })
    },
    moveElement() {
      this.recalculatePosition();
    },
    removeState(element) {
      this.states.splice(element.position, 1);
      this.recalculatePosition();
    },
    recalculatePosition(){
      for(let i = 0; i < this.states.length; i++){
        this.states[i].position = i;
      }
    }
  },
}
</script>
