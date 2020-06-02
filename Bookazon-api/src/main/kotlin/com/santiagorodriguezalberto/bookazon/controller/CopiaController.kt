package com.santiagorodriguezalberto.bookazon.controller

import com.santiagorodriguezalberto.bookazon.dtos.BibliotecaDTO
import com.santiagorodriguezalberto.bookazon.dtos.CopiaDTO
import com.santiagorodriguezalberto.bookazon.dtos.toBibliotecaDTO
import com.santiagorodriguezalberto.bookazon.dtos.toCopiaDTO
import com.santiagorodriguezalberto.bookazon.entity.Biblioteca
import com.santiagorodriguezalberto.bookazon.entity.Copia
import com.santiagorodriguezalberto.bookazon.service.BibliotecaService
import com.santiagorodriguezalberto.bookazon.service.CopiaService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/copias")
class CopiaController(
        val copiaService: CopiaService
) {
    private fun allCopias(biblioteca_name: String) : List<Copia> {
        var result: List<Copia>
        with(copiaService) {
            result = findAllByBiblioteca(biblioteca_name)

        }
        if (result.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No hay ninguna copia para esta biblioteca")
        return result
    }


    @GetMapping("/biblioteca/{biblioteca_name}")
    @ApiOperation(value = "Deveulve una lista con todas las copias de la biblioteca que como nombre tiene el que se le ha pasado por parámetro")
    fun getCopias(@PathVariable biblioteca_name: String) = allCopias(biblioteca_name).map {
        it.toCopiaDTO()
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Devuelve una copia cuyo id coincide con el que se ha pasado como parámetro")
    fun getCopia(@PathVariable id: UUID): CopiaDTO {
        var result = copiaService.findById(id)

        if (result.isPresent) return result.get().toCopiaDTO()
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la copia con el identificador $id")
    }
}