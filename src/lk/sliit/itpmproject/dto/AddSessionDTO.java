package lk.sliit.itpmproject.dto;

public class AddSessionDTO {
    int id;
    String selectLecture;
    String selectTag;
    String selectedLecturer;
    String selectGroup;
    int noOfStudent;
    String selectSubject;
    int durationHrs;
    String room;

    public AddSessionDTO() {
    }

    public AddSessionDTO(int id, String selectLecture, String selectTag, String selectedLecturer, String selectGroup, int noOfStudent, String selectSubject, int durationHrs, String room) {
        this.id = id;
        this.selectLecture = selectLecture;
        this.selectTag = selectTag;
        this.selectedLecturer = selectedLecturer;
        this.selectGroup = selectGroup;
        this.noOfStudent = noOfStudent;
        this.selectSubject = selectSubject;
        this.durationHrs = durationHrs;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelectLecture() {
        return selectLecture;
    }

    public void setSelectLecture(String selectLecture) {
        this.selectLecture = selectLecture;
    }

    public String getSelectTag() {
        return selectTag;
    }

    public void setSelectTag(String selectTag) {
        this.selectTag = selectTag;
    }

    public String getSelectedLecturer() {
        return selectedLecturer;
    }

    public void setSelectedLecturer(String selectedLecturer) {
        this.selectedLecturer = selectedLecturer;
    }

    public String getSelectGroup() {
        return selectGroup;
    }

    public void setSelectGroup(String selectGroup) {
        this.selectGroup = selectGroup;
    }

    public int getNoOfStudent() {
        return noOfStudent;
    }

    public void setNoOfStudent(int noOfStudent) {
        this.noOfStudent = noOfStudent;
    }

    public String getSelectSubject() {
        return selectSubject;
    }

    public void setSelectSubject(String selectSubject) {
        this.selectSubject = selectSubject;
    }

    public int getDurationHrs() {
        return durationHrs;
    }

    public void setDurationHrs(int durationHrs) {
        this.durationHrs = durationHrs;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "AddSessionDTO{" +
                "id=" + id +
                ", selectLecture='" + selectLecture + '\'' +
                ", selectTag='" + selectTag + '\'' +
                ", selectedLecturer='" + selectedLecturer + '\'' +
                ", selectGroup='" + selectGroup + '\'' +
                ", noOfStudent=" + noOfStudent +
                ", selectSubject='" + selectSubject + '\'' +
                ", durationHrs=" + durationHrs +
                ", room='" + room + '\'' +
                '}';
    }
}
