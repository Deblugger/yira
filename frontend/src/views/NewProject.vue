<template>
  <div>
    <form novalidate class="md-layout" @submit.prevent="saveProject">
      <md-button type="submit" class="md-primary" :disabled="sending">Create project</md-button>
      <md-field>
        <label for="project-name">Project Name</label>
        <md-input name="project-name" id="project-name" v-model="projectName" :disabled="sending" />
      </md-field>
      <md-field>
        <label for="state-name">New state</label>
        <md-input name="state-name" id="state-name" v-model="newState" :disabled="sending" />
        <md-button @click="addNewState()" class="md-primary md-raised">Add</md-button>
      </md-field>

      <draggable v-model="states" @start="drag=true" @change="moveElement" @end="drag=false">
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
      "lastPosition": 0
    }
  },
  methods: {
    addNewState() {
      this.states.push({
        "name": this.newState,
        "position": this.lastPosition
      });
      this.newState = "";
      this.lastPosition++;
    },
    saveProject() {
      console.log(this.states);
      axios.post("http://localhost:8080/projects", {
        "name": this.projectName,
        "owner": this.owner,
        "states": this.states
      }).then(res => console.log(res))
    },
    moveElement() {
      /*let moveInfo = event.moved;
      this.states[moveInfo.newIndex].position = moveInfo.newIndex;
      for(let i = moveInfo.newIndex + 1; i < this.states.length; i++){
        this.states[i].position++;
      }*/
      this.recalculatePosition();
    },
    removeState(element) {
      console.log(element);
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
