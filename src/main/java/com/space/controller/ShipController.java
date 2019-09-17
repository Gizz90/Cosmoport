package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipFilterService;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private ShipFilterService filterService;

    @GetMapping(value = "/ships")
    public List<Ship> getAllShips(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "planet", required = false) String planet,
                                  @RequestParam(value = "shipType", required = false) ShipType shipType,
                                  @RequestParam(value = "after", required = false) Long after,
                                  @RequestParam(value = "before", required = false) Long before,
                                  @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                  @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                  @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                  @RequestParam(value = "minRating", required = false) Double minRating,
                                  @RequestParam(value = "maxRating", required = false) Double maxRating,
                                  @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        return shipService.getAllShips(Specification.where(filterService.filterByName(name)
                .and(filterService.filterByPlanet(planet))
                .and(filterService.filterByType(shipType))
                .and(filterService.filterByProdDate(after, before))
                .and(filterService.filterByUsage(isUsed))
                .and(filterService.filterBySpeed(minSpeed, maxSpeed))
                .and(filterService.filterByCrewSize(minCrewSize, maxCrewSize))
                .and(filterService.filterByRating(minRating, maxRating))), pageable).getContent();
    }

    @GetMapping(value = "/ships/count")
    public Integer getShipsCount(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "planet", required = false) String planet,
                                 @RequestParam(value = "shipType", required = false) ShipType shipType,
                                 @RequestParam(value = "after", required = false) Long after,
                                 @RequestParam(value = "before", required = false) Long before,
                                 @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                 @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                 @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                 @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                 @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                 @RequestParam(value = "minRating", required = false) Double minRating,
                                 @RequestParam(value = "maxRating", required = false) Double maxRating) {

        return shipService.getAllShips(Specification.where(filterService.filterByName(name)
                .and(filterService.filterByPlanet(planet))
                .and(filterService.filterByType(shipType))
                .and(filterService.filterByProdDate(after, before))
                .and(filterService.filterByUsage(isUsed))
                .and(filterService.filterBySpeed(minSpeed, maxSpeed))
                .and(filterService.filterByCrewSize(minCrewSize, maxCrewSize))
                .and(filterService.filterByRating(minRating, maxRating))))
                .size();
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


