package me.deblugger.state

import javax.persistence.*

@Entity
@Table(name = "states")
data class StateEntity (
        var name: String,
        var projectId: Long, //ProjectId
        var position: Long
) {
        @Id
        @GeneratedValue
        var id: Long = 1
}