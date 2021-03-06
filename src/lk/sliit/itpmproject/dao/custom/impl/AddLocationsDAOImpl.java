package lk.sliit.itpmproject.dao.custom.impl;

import lk.sliit.itpmproject.dao.CrudUtil;
import lk.sliit.itpmproject.dao.custom.AddLocationsDAO;
import lk.sliit.itpmproject.entity.AddLocations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddLocationsDAOImpl implements AddLocationsDAO {
    @Override
    public List<AddLocations> findAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM AddLocations");
        List<AddLocations> addLocationsList = new ArrayList<>();
        while (resultSet.next()){
            addLocationsList.add(new AddLocations(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getBoolean(4),
                    resultSet.getBoolean(5),
                    resultSet.getString(6)
                    ));
        }
        return addLocationsList;
    }

    @Override
    public AddLocations find(String s)  {
        return null;
    }

    @Override
    public boolean save(AddLocations entity) throws SQLException {

        return CrudUtil.execute("INSERT INTO AddLocations VALUES (?,?,?,?,?,?)",
                entity.getId(),
                entity.getBuildingName(),
                entity.getRoomName(),
                entity.isLectureHall(),
                entity.isLaboratory(),
                entity.getCapacity()
        );
    }

    @Override
    public boolean update(AddLocations entity) throws SQLException {
        return CrudUtil.execute("UPDATE AddLocations SET buildingName = ?, roomName =?, lectureHall=?, laboratory=?, Capacity=? WHERE id = ?",
                entity.getBuildingName(),
                entity.getRoomName(),
                entity.isLectureHall(),
                entity.isLaboratory(),
                entity.getCapacity(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return CrudUtil.execute("DELETE FROM AddLocations WHERE id=?", s);
    }

    @Override
    public int getLastLocationID() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT id FROM AddLocations ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return 0;
        }
    }
}
