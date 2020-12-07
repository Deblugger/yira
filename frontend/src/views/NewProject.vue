<template>
  <div>
    <b-form>
      <b-button class="mr-1" variant="success" v-if="this.projectId === undefined" @click="saveProject">Create project</b-button>
      <b-button class="mr-1" variant="primary" v-if="this.projectId !== undefined" @click="updateProject">Update project</b-button>
      <b-button class="mr-1" variant="danger" v-if="this.projectId !== undefined" @click="showDialog = true">Remove</b-button>
      <b-form-group
        label="Project Name"
        label-for="project-name"
        description="The project name."
      >
        <b-form-input
          id="project-name"
          v-model="projectName"
          type="text"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group v-if="this.projectId === undefined"
        label="New state"
        label-for="state-name"
        description="This will be a column for your project tasks."
      >
        <b-form-input
          id="state-name"
          v-model="newState"
          type="text"
        ></b-form-input>
        <b-button @click="addNewState()" variant="primary">Add</b-button>
      </b-form-group>
      <draggable v-if="this.projectId === undefined" v-model="states" @start="drag=true" @change="moveElement" @end="drag=false">
        <div v-for="element in states" :key="element.name">
          <b-card
            title="Card Title"
            style="max-width: 20rem;"
            class="mb-2"
          >
            <b-card-text>
              {{element.name}} <b-badge>{{element.position}}</b-badge>
            </b-card-text>

            <b-button variant="danger" @click="removeState(element)">Remove</b-button>
          </b-card>
        </div>
      </draggable>
    </b-form>

    <!-- Delete Project Dialog -->
    <b-modal v-if="this.projectId !== undefined" id="removeConfirmationModal" v-model="showDialog" hide-footer>
      <template #modal-title>
        Hey!
      </template>
      <div class="d-block text-center">
        You're going to delete a whole project, including tasks. Are you sure?
      </div>
      <b-button class="mt-3" block @click="showDialog = false">No</b-button>
      <b-button class="mt-3" block @click="deleteProject">Yes</b-button>
    </b-modal>
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
