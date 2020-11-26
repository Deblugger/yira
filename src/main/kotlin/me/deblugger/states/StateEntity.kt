package me.deblugger.states

import me.deblugger.converter.StringListConverter
import javax.persistence.*

@Entity
@Table(name = "states")
data class StateEntity (
        @Id
        @GeneratedValue
        var id: Long = 1,
        var name: String,
        var projectId: Long //ProjectId

//TODO: Add order
)