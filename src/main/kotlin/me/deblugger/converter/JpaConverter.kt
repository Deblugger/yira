package me.deblugger.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class StringListConverter: AttributeConverter<List<String>, String> {
    override fun convertToDatabaseColumn(attribute: List<String>?) = attribute?.joinToString(",")

    override fun convertToEntityAttribute(value: String?) = value?.split(",")

}