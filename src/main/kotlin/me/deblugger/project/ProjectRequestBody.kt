package me.deblugger.project

import me.deblugger.states.StateOrderedCreationRequestBody

data class ProjectCreationRequestBody(var name: String, var owner: Long, var states: List<StateOrderedCreationRequestBody>)

data class ProjectUpdateRequestBody(var name: String?, var owner: Long?)
