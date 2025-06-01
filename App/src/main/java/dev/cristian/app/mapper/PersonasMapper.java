package dev.cristian.app.mapper;

import dev.cristian.app.dto.personas.PersonasActualizarDto;
import dev.cristian.app.dto.personas.PersonasCreatDto;
import dev.cristian.app.dto.personas.PersonasDetalleDto;
import dev.cristian.app.entity.Personas;
import org.springframework.stereotype.Component;

@Component
public class PersonasMapper {

    public Personas toEntity(PersonasCreatDto dto) {
        Personas persona = new Personas();
        persona.setNombre(dto.getNombre());
        persona.setApellidos(dto.getApellidos());
        persona.setCurp(dto.getCurp());
        persona.setTelefono(dto.getTelefono());
        persona.setEdad(dto.getEdad());
        persona.setCorreo(dto.getCorreo());
        return persona;
    }

    public PersonasCreatDto toCrearDto(Personas persona) {
        PersonasCreatDto dto = new PersonasCreatDto();
        dto.setNombre(persona.getNombre());
        dto.setApellidos(persona.getApellidos());
        dto.setCurp(persona.getCurp());
        dto.setTelefono(persona.getTelefono());
        dto.setEdad(persona.getEdad());
        dto.setCorreo(persona.getCorreo());
        return dto;
    }

    public PersonasDetalleDto toDetalleDto(Personas persona) {
        PersonasDetalleDto dto = new PersonasDetalleDto();
        dto.setNombre(persona.getNombre());
        dto.setApellidos(persona.getApellidos());
        dto.setCurp(persona.getCurp());
        dto.setTelefono(persona.getTelefono());
        dto.setEdad(persona.getEdad());
        dto.setCorreo(persona.getCorreo());
        return dto;
    }

    public PersonasActualizarDto toActualizarDto(Personas persona) {
        PersonasActualizarDto dto = new PersonasActualizarDto();
        dto.setNombre(persona.getNombre());
        dto.setApellidos(persona.getApellidos());
        dto.setCurp(persona.getCurp());
        dto.setTelefono(persona.getTelefono());
        dto.setEdad(persona.getEdad());
        dto.setCorreo(persona.getCorreo());
        return dto;
    }

    public void actualizarDesdeDto(Personas persona, PersonasActualizarDto dto) {
        if (dto.getNombre() != null) {
            persona.setNombre(dto.getNombre());
        }
        if (dto.getApellidos() != null) {
            persona.setApellidos(dto.getApellidos());
        }
        if (dto.getCurp() != null) {
            persona.setCurp(dto.getCurp());
        }
        if (dto.getTelefono() != null) {
            persona.setTelefono(dto.getTelefono());
        }
        if (dto.getEdad() != null) {
            persona.setEdad(dto.getEdad());
        }
        if (dto.getCorreo() != null) {
            persona.setCorreo(dto.getCorreo());
        }
    }
}