package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public List<Ship> getAllShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order, Integer pageNumber, Integer pageSize) {
        return shipRepository.findAll();
    }

     @Override
    public Ship createShip(Ship ship) {
        if (ship.getName() == null
                || ship.getPlanet() == null
                || ship.getShipType() == null
                || ship.getProdDate() == null
                || ship.getSpeed() == null
                || ship.getCrewSize() == null) {
            throw new BadRequestException("One of the fields is empty");
        }
        checkShipFields(ship);

        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }

        Double rating = calculateRating(ship);
        ship.setRating(rating);

        return shipRepository.saveAndFlush(ship);
    }

    @Override
    public Ship getShipById(Long id) {
        if (id == 0) {
            throw new BadRequestException("Invalid id");
        }
        if (!shipRepository.existsById(id)) {
            throw new ShipNotFoundException("Ship not found");
        }
        Optional<Ship> optional = shipRepository.findById(id);
        return optional.get();
    }

    @Override
    public Ship updateShip(Long id, Ship ship) {
        checkShipFields(ship);
        if (id == 0) {
            throw new BadRequestException("Invalid id");
        }
        if (!shipRepository.existsById(id)) {
            throw new ShipNotFoundException("Ship not found");
        }
        Ship updatedShip = shipRepository.findById(id).get();
        if (ship.getName() != null) {
            updatedShip.setName(ship.getName());
        }
        if (ship.getPlanet() != null) {
            updatedShip.setPlanet(ship.getPlanet());
        }
        if (ship.getShipType() != null) {
            updatedShip.setShipType(ship.getShipType());
        }
        if (ship.getProdDate() != null) {
            updatedShip.setProdDate(ship.getProdDate());
        }
        if (ship.getUsed() != null) {
            updatedShip.setUsed(ship.getUsed());
        }
        if (ship.getSpeed() != null) {
            updatedShip.setSpeed(ship.getSpeed());
        }
        if (ship.getCrewSize() != null) {
            updatedShip.setCrewSize(ship.getCrewSize());
        }
        Double rating = calculateRating(updatedShip);
        updatedShip.setRating(rating);
        return shipRepository.saveAndFlush(updatedShip);
    }

    @Override
    public void deleteShipById(Long id) {
        if (id == 0) {
            throw new BadRequestException("Invalid id");
        }
        if (!shipRepository.existsById(id)) {
            throw new ShipNotFoundException("Ship not found");
        }
        shipRepository.deleteById(id);
    }

    private Double calculateRating(Ship ship) {
        double k = ship.getUsed() ? 0.5 : 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ship.getProdDate());
        int prodYear = calendar.get(Calendar.YEAR);

        double rating = 80 * ship.getSpeed() * k / (3019 - prodYear + 1);
        BigDecimal bd = new BigDecimal(rating).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void checkShipFields(Ship ship) {
        if (ship.getName() != null && (ship.getName().length() < 1 || ship.getName().length() > 50)) {
            throw new BadRequestException("Incorrect name. Set length of ship name between 1..50 chars");
        }
        if (ship.getPlanet() != null && (ship.getPlanet().length() < 1 || ship.getPlanet().length() > 50)) {
            throw new BadRequestException("Incorrect planet. Set length of planet name between 1..50 chars");
        }
        if (ship.getSpeed() != null && (ship.getSpeed() < 0.01 || ship.getSpeed() > 0.99)) {
            throw new BadRequestException("Incorreect speed. Set speed between 0.01..0.99");
        }
        if (ship.getCrewSize() != null && (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999)) {
            throw new BadRequestException("Incorrect crewSize. Set crewSize between 1..9999");
        }
        if (ship.getProdDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ship.getProdDate());
            if (calendar.get(Calendar.YEAR) < 2800 || calendar.get(Calendar.YEAR) > 3019) {
                throw new BadRequestException("Incorrect prodDate. Set prodDate between 2800..3019");
            }
        }
    }
}
