package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @GetMapping(value = "/ships")
    public List<Ship> getAllShips() {
        return null;
    }

    @GetMapping(value = "/ships/count")
    public Integer getShipsCount() {
        return null;
    }

    @PostMapping(value = "/ships")
    public Ship createShip(@RequestBody Ship ship) {
        return shipService.createShip(ship);
    }

    @GetMapping(value = "/ships/{id}")
    public Ship getShip(@PathVariable(value = "id") Long id) {
        return shipService.getShipById(id);
    }

    @PostMapping(value = "ships/{id}")
    public Ship updateShip(@PathVariable(value = "id") Long id, @RequestBody Ship ship) {
        return shipService.updateShip(id, ship);
    }

    @DeleteMapping(value = "ships/{id}")
    public void deleteShipById(@PathVariable(value = "id") Long id) {
        shipService.deleteShipById(id);

    }

}


