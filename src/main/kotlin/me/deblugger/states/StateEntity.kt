package me.deblugger.states

import javax.persistence.*

@Entity
@Table(name = "states")
data class StateEntity (
        var name: String,
        var projectId: Long //ProjectId

//TODO: Add order
) {
        @Id
        @GeneratedValue
        var id: Long = 1
}