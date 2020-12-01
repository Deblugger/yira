package me.deblugger.states

data class StateCreationRequestBody(val name: String, val position: Long, val projectId: Long)

data class StateOrderedCreationRequestBody(val name: String, val position: Long)

data class StateUpdateRequestBody(val name: String)