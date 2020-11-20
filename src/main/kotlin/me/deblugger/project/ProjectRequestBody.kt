package me.deblugger.project

data class ProjectCreationRequestBody(var name: String, var owner: String, var states: List<String>)

data class ProjectUpdateRequestBody(var name: String?, var states: List<String>?)
