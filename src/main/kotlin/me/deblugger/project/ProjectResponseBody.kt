package me.deblugger.project

data class ProjectResponseBody(
        val id: Long,
        val name: String,
        val owner: Long,
        val states: List<String>
)

fun ProjectEntity.toResponseBody(states: List<String>) = ProjectResponseBody(
        this.id!!, this.name, this.owner, states
)