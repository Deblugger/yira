package me.deblugger.project

data class ProjectCreationRequestBody(var name: String, var owner: Long, var states: List<String>)

data class ProjectUpdateRequestBody(var name: String?, var owner: Long?)
