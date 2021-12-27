package com.unmsm.springboot.backend.ecommerce.controllers;


import com.unmsm.springboot.backend.ecommerce.models.entity.Users;
import com.unmsm.springboot.backend.ecommerce.models.services.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    private IUsersService usersService;

    @GetMapping("/getAll")
    public List<Users> getAll() {
        return usersService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Users users, BindingResult result) {

        Users usersNew = null;
        Map<String, Object> response = new HashMap<>();

        // primero validamos si la estructura que
        // viene cumple con las validaciones que se declararon en el modelo del objeto
        if(result.hasErrors()) {
            // SI se obtuviera algun error de validacion ejecutamos lo siguiente
            // para que el sistema muestre el error especifico (Enviamos un 400 -> BAD_REQUEST )
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err-> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            // (Enviamos un 400 -> BAD_REQUEST )
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        // Verificamos si se realizo con exito la insercion del usuario
        // (evaluamos con un try catch para ver si dispara algun error)
        try {
            users.setCreated_at(new Date());
            users.setUpdated_at(new Date());
            usersNew = usersService.save(users);
        } catch (DataAccessException e) {
            // En caso se dispare algun error disparamos el siguiente response con el error especificando
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            // (Enviamos un 500 -> INTERNAL_SERVER_ERROR )
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ah sido creado con éxito!!");
        response.put("Usuario", usersNew );

        // (Enviamos un 201 -> CREATED )
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody Users users, BindingResult result) {
        Users usersActual = usersService.findById(users.getId());
        Users usersUpdate = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err-> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        // verificamos si el usuario actual existe
        if (usersActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el usuario: ");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        // Verificamos si se realizo con exito la actualizacion del usuario
        // (evaluamos con un try catch para ver si dispara algun error)
        try {
            usersActual.setName(users.getName());
            usersActual.setLastname(users.getLastname());
            usersActual.setPhone(users.getPhone());
            usersActual.setPassword(users.getPassword());
            usersActual.setIs_available(users.getIs_available());
            usersActual.setCreated_at(users.getCreated_at());
            usersActual.setUpdated_at(new Date());

            usersUpdate = usersService.save(usersActual);

        } catch (DataAccessException e) {
            // En caso se dispare algun error disparamos el siguiente response con el error especificando
            response.put("mensaje", "Error al realizar el update en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            // (Enviamos un 500 -> INTERNAL_SERVER_ERROR )
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ah sido actualizado con éxito!!");
        response.put("Usuario", usersUpdate );

        // (Enviamos un 201 -> CREATED )
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        // @PathVariable significa www.delfosti.tracking/tracking/1
        // @RequestParam significa www.delfosti.tracking/tracking?trackingId=1
        Users users = null;
        Map<String, Object> response = new HashMap<>();

        try {
            users = usersService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (users == null) {
            response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        // (Enviamos un 201 -> CREATED )
        return new ResponseEntity<Users>(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users, BindingResult result) {

        Users usersValidate = null;

        Map<String, Object> response = new HashMap<>();

        // Verificamos si se realizo con exito la actualizacion del usuario
        // (evaluamos con un try catch para ver si dispara algun error)
        try {
            usersValidate = usersService.findByEmail(users.getEmail());

            // verificamos si el usuario actual existe
            if (usersValidate == null) {
                response.put("message", "Error: no existe el usuario ");
                response.put("success", false);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_IMPLEMENTED);
            }

            if (usersValidate.getPassword().equals(users.getPassword())) {
                response.put("message", "El usuario ah sido autenticado con éxito!!");
                response.put("data", usersValidate);
                response.put("success", true);

                // (Enviamos un 201 -> VERIFIED )
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } else {
                response.put("message", "Error: Contrasenia invalida ");
                response.put("success", false);
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
            }

        } catch (DataAccessException e) {
            // En caso se dispare algun error disparamos el siguiente response con el error especificando
            response.put("message", "Error al encontrar el usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            response.put("success", false);

            // (Enviamos un 500 -> INTERNAL_SERVER_ERROR )
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
