package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {

    Page<Ship> getAllShips(Specification<Ship> specification, Pageable pageable);

    List<Ship> getAllShips(Specification<Ship> specification);

    Ship createShip(Ship ship);

    Ship getShipById(Long id);

    Ship updateShip(Long id, Ship updatedShip);

    void deleteShipById(Long id);
}
