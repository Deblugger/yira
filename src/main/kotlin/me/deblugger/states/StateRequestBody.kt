package me.deblugger.states

data class StateCreationRequestBody(val name: String, val projectId: Long)

data class StateUpdateRequestBody(val name: String)