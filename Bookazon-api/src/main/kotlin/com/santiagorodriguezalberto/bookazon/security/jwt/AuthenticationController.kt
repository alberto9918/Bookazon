package com.santiagorodriguezalberto.bookazon.security.jwt

import com.santiagorodriguezalberto.bookazon.dtos.UserDTO
import com.santiagorodriguezalberto.bookazon.dtos.toUserDTO
import com.santiagorodriguezalberto.bookazon.entity.Usuario
import io.swagger.annotations.ApiOperation

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class AuthenticationController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/auth/login")
    @ApiOperation(value = "Método para loguearte en la aplicación",
            notes = "Devuelve el usuario(si existe) con un token con el que poder acceder a los métodos")
    fun login(@Valid @RequestBody loginRequest : LoginRequest) : JwtUserResponse {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.username, loginRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = authentication.principal as Usuario
        val jwtToken = jwtTokenProvider.generateToken(authentication)

        return JwtUserResponse(jwtToken, user.toUserDTO())

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    @ApiOperation(value = "Obtiene el usuario que está logueado en ese momento")
    fun me(@AuthenticationPrincipal user : Usuario) = user.toUserDTO()




}


data class LoginRequest(
        @NotBlank val username : String,
        @NotBlank val password: String
)

data class JwtUserResponse(
        val token: String,
        val user : UserDTO
)