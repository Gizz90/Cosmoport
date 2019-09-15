package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.awt.print.Pageable;
import java.util.List;

public interface ShipService {

    List<Ship> getAllShips(String name, String planet, ShipType shipType, Long after, Long before,
                           Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize,
                           Double minRating, Double maxRating, ShipOrder order, Integer pageNumber, Integer pageSize);

    List<Ship> getAllShips(Specification<Ship> specification, Pageable pageable);

    Ship createShip(Ship ship);

    Ship getShipById(Long id);

    Ship updateShip(Long id, Ship updatedShip);

    void deleteShipById(Long id);

    Long parseAndGetId(String id);

}
