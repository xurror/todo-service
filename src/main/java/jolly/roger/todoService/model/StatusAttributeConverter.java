package jolly.roger.todoService.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusAttributeConverter implements AttributeConverter<Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Status attribute) {
        return attribute.getCode();
    }

    @Override
    public Status convertToEntityAttribute(Integer dbData) {
        return Status.fromInt(dbData);
    }
}
