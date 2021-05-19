package lk.sliit.itpmProject.business.custom;

import lk.sliit.itpmProject.business.SuperBO;
import lk.sliit.itpmProject.dto.AddSessionDTO;
import lk.sliit.itpmProject.dto.AddSessionNALectureDTO;
import lk.sliit.itpmProject.dto.LoadSessionDataDTO;
import lk.sliit.itpmProject.dto.ManageNotAvbTimeDTO;

import java.util.List;

public interface SessionManageBO extends SuperBO {
    int getLastItemCode() throws Exception;

    void saveSession(AddSessionDTO addSessionDTO) throws Exception;

    List<LoadSessionDataDTO> loadSessionTable() throws Exception;


    AddSessionDTO findAllSessions(String id) throws Exception;

    int getLastNotAvbLectures() throws Exception;

    void saveNASessionLec(AddSessionNALectureDTO addSessionNALectureDTO) throws Exception;

    List<ManageNotAvbTimeDTO> findAllData() throws Exception;

    void updateSession(AddSessionDTO addSessionDTO2) throws Exception;


    boolean deleteItem(String id) throws Exception;

    List<AddSessionDTO> findAllSessions() throws Exception;

    void saveNATimeAlocations(List<LoadSessionDataDTO> dtos) throws Exception;

    List<LoadSessionDataDTO> loadSessionTableSearch(int i, String val) throws Exception;

    void saveRoom(String val1, String val2) throws Exception;

    void savetblParallel(List<LoadSessionDataDTO> dtos) throws Exception;

    void savetblNonOverLapping(List<LoadSessionDataDTO> dtos) throws Exception;
}
