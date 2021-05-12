package lk.sliit.itpmProject.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.sliit.itpmProject.business.BOFactory;
import lk.sliit.itpmProject.business.BOTypes;
import lk.sliit.itpmProject.business.custom.*;
import lk.sliit.itpmProject.dto.*;
import lk.sliit.itpmProject.util.TagsTM;

public class AddsessionController implements Initializable {

    public TextField cmb_selected_lecture;
    public ChoiceBox cmb_select_tag;
    public ChoiceBox cmb_select_lecture;
    public ChoiceBox cmb_select_group;
    public ChoiceBox cmb_select_subject;
    public TextField cmb_No_of_Student;
    public TextField cmb_select_duration_hrs;
    @FXML
    private AnchorPane root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView iconHome;

    @FXML
    private ImageView iconLecture;

    @FXML
    private ImageView iconStudent;

    @FXML
    private ImageView iconTimeTable;

    @FXML
    private ImageView iconLocation;
    private final AddStudentBO addStudentBO = BOFactory.getInstance().getBO(BOTypes.AddStudent);
    private final AddLecturerBO addLecturerBO = BOFactory.getInstance().getBO(BOTypes.AddLecturer);
    private final AddTagBO addTagBO = BOFactory.getInstance().getBO(BOTypes.AddTag);
    private final AddSubjectBO addSubjectBO = BOFactory.getInstance().getBO(BOTypes.AddSubject);
    private final SessionManageBO sessionManageBO = BOFactory.getInstance().getBO(BOTypes.AddSession);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            List<AddLecturerDTO> addLecturerDTOList = addLecturerBO.findAllLecturersName();

            ObservableList lecturerTMS = cmb_select_lecture.getItems();
            cmb_select_lecture.setValue("Select Name");
            for (AddLecturerDTO addLecturerDtO: addLecturerDTOList) {
                lecturerTMS.add(addLecturerDtO.getlName());
            }
        }catch (Exception e){
        }

        try{
            List<AddSubjectDTO> addSubjectDTOList = addSubjectBO.findAllSubjects();
            ObservableList<String> subject = cmb_select_subject.getItems();
            for (AddSubjectDTO addSubjectDTO : addSubjectDTOList){
                subject.add(addSubjectDTO.getSubName());
            }
        }catch(Exception e){

        }

        try{
            List<AddStudentDTO> addStudentDTOList = addStudentBO.findAllStudent();
            ObservableList<String> studentTMS = cmb_select_group.getItems();
            for (AddStudentDTO addStudentDTO:addStudentDTOList) {
                studentTMS.add(addStudentDTO.getSubGroupId());
            }
        }catch(Exception e){

        }

        try{
            List<AddTagDTO> addTagDTOList = addTagBO.findAllTags();
            ObservableList tag = cmb_select_tag.getItems();
            for (AddTagDTO addTagDTO: addTagDTOList) {
                tag.add(addTagDTO.getTagName());
            }
        }catch (Exception e){

        }

    }
    public void btn_onaction_submit(ActionEvent event) {
        int maxID = 0;
        try{
            int lastItemCode = sessionManageBO.getLastItemCode();
            if(lastItemCode == 0){
                maxID = 1;
            }
            else{
                maxID = lastItemCode + 1;
            }
        }catch(Exception e){
            new Alert(Alert.AlertType.INFORMATION, "Something went wrong").show();
        }

        int No_of_Student = Integer.parseInt(cmb_No_of_Student.getText());
        String lectureTxt = cmb_selected_lecture.getText();
        String select_lecture = (String) cmb_select_lecture.getValue();
        String select_tag = (String) cmb_select_tag.getValue();
        String select_group = (String) cmb_select_group.getValue();
        String select_Subject = (String) cmb_select_subject.getValue();
        int select_duration_hrs = Integer.parseInt(cmb_select_duration_hrs.getText());

        AddSessionDTO addSessionDTO = new AddSessionDTO(
                maxID,
                select_lecture,
                select_tag,
                lectureTxt,
                select_group,
                No_of_Student,
                select_Subject,
                select_duration_hrs
        );

        try {
            sessionManageBO.saveSession(addSessionDTO);
            new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();

        } catch (Exception e) {
            System.out.println(e);
        }
        cmb_selected_lecture.setText("");
        cmb_No_of_Student.setText("");
        cmb_select_duration_hrs.setText("");
        cmb_select_lecture.setValue(null);
        cmb_select_tag.setValue(null);
        cmb_select_group.setValue(null);
        cmb_select_subject.setValue(null);

    }

    @FXML
    void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            FXMLLoader fxmlLoader = null;
            switch (icon.getId()) {
                case "iconHome":
                    root = FXMLLoader.load(this.getClass().getResource("../view/MainForm.fxml"));
                    break;
                case "iconStudent":
                    root = FXMLLoader.load(this.getClass().getResource("../view/AddStudent.fxml"));
                    break;
                case "iconLecture":
                    root = FXMLLoader.load(this.getClass().getResource("../view/AddLecturer.fxml"));
                    break;
                case "iconTimeTable":
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/AddWorkingDaysAndHours.fxml"));
                    root = fxmlLoader.load();
                    break;
                case "iconLocation":
                    root = FXMLLoader.load(this.getClass().getResource("../view/AddRBLocation.fxml"));
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();

                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }

    @FXML
    void playMouseEnterAnimation(MouseEvent event) {

    }

    @FXML
    void playMouseExitAnimatio(MouseEvent event) {

    }

    @FXML
    void initialize() {

    }

    public void btn_NEXT_ONACTION(ActionEvent event) {
    }

    public void btn_onaction_clear(ActionEvent event) {
    }


    public void btn_onaction_back(ActionEvent event) {
    }


}
