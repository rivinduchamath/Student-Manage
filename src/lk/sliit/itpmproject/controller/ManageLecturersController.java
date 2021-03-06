package lk.sliit.itpmproject.controller;

import com.jfoenix.controls.JFXComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.sliit.itpmproject.business.BOFactory;
import lk.sliit.itpmproject.business.BOTypes;
import lk.sliit.itpmproject.business.custom.AddLecturerBO;
import lk.sliit.itpmproject.dto.AddLecturerDTO;
import lk.sliit.itpmproject.util.LecturerTM;

public class ManageLecturersController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<LecturerTM> tblLecturer;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TextField txtLname;

    @FXML
    private TextField txtLemid;

    @FXML
    private TextField txtRank;

    @FXML
    private JFXComboBox<String> cmbFaculty;

    @FXML
    private JFXComboBox<String> cmbDepartment;

    @FXML
    private JFXComboBox<String> cmbCenter;

    @FXML
    private JFXComboBox<String> cmbBuilding;

    @FXML
    private JFXComboBox<String> cmbLevel;

    private final AddLecturerBO addLecturerBO = BOFactory.getInstance().getBO(BOTypes.ADD_LECTURER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblLecturer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblLecturer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empId"));
        tblLecturer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tblLecturer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("department"));
        tblLecturer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("faculty"));
        tblLecturer.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("center"));
        tblLecturer.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("buildingNo"));
        tblLecturer.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("level"));
        tblLecturer.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("rank"));

        ObservableList<String> list1;

        list1 = cmbCenter.getItems();
        list1.add("Malabe");
        list1.add("Metro");
        list1.add("Matara");
        list1.add("Kandy");
        list1.add("Kurunagala");
        list1.add("Jaffna");

        ObservableList<String> list2;
        list2 = cmbLevel.getItems();
        list2.add("Professor");
        list2.add("Assistant Professor");
        list2.add("Senior Lecturer(HG)");
        list2.add("Senior Lecturer");
        list2.add("Lecturer");
        list2.add("Assistant Lecturer");

        ObservableList<String> list33;
        list33 = cmbFaculty.getItems();
        list33.add("Computing Faculty");
        list33.add("Bussiness Faculty");
        list33.add("Engineering Faculty");
        list33.add("Architecture Faculty");
        list33.add("Faculty of Humanities and Science");

        ObservableList<String> list44;
        list44 = cmbDepartment.getItems();
        list44.add("IT");
        list44.add("SE");
        list44.add("ISE");
        list44.add("DS");
        list44.add("CS");
        list44.add("IM");
        list44.add("CSNE");
        list44.add("Civil");
        list44.add("Electronic Engineering");
        list44.add("Mechanical Engineering");
        list44.add("QS");

        ObservableList<String> list55;
        list55 = cmbBuilding.getItems();
        list55.add("Block A");
        list55.add("Block B");
        list55.add("Block E");
        list55.add("Block F");

        try {
            List<AddLecturerDTO> addLecturerDTOList = addLecturerBO.findAllLecturers();
            ObservableList<LecturerTM> lecturerTMS = tblLecturer.getItems();
            for (AddLecturerDTO addLecturerDtO : addLecturerDTOList) {
                lecturerTMS.add(new LecturerTM(
                        addLecturerDtO.getId(),
                        addLecturerDtO.getEmpId(),
                        addLecturerDtO.getlName(),
                        addLecturerDtO.getDepartment(),
                        addLecturerDtO.getFaculty(),
                        addLecturerDtO.getCenter(),
                        addLecturerDtO.getBuildingNo(),
                        addLecturerDtO.getLevel(),
                        addLecturerDtO.getRank()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblLecturer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            LecturerTM selectedItem = tblLecturer.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                btnDelete.setDisable(true);
                return;
            }

            btnDelete.setDisable(false);

            cmbBuilding.setValue(selectedItem.getBuildingNo());
            cmbFaculty.setValue(selectedItem.getFaculty());
            cmbCenter.setValue(selectedItem.getCenter());
            cmbLevel.setValue(selectedItem.getLevel());
            cmbDepartment.setValue(selectedItem.getDepartment());
            txtLemid.setText(selectedItem.getEmpId());
            txtLname.setText(selectedItem.getLastName());
            txtRank.setText(selectedItem.getRank());
        });
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to clear?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        txtLname.setText("");
        txtLemid.setText("");
        txtRank.setText("");
        cmbDepartment.setValue(null);
        cmbFaculty.setValue(null);
        cmbCenter.setValue(null);
        cmbBuilding.setValue(null);
        cmbLevel.setValue(null);
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Detail?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        boolean val = buttonType.isPresent();
        if (val) {
            ButtonType boll = buttonType.get();
            if (boll == ButtonType.YES) {
                LecturerTM selectedItem = tblLecturer.getSelectionModel().getSelectedItem();
                try {
                    addLecturerBO.deleteItem(selectedItem.getId());
                    tblLecturer.getItems().remove(selectedItem);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.INFORMATION, "Something went wrong").show();
                    Logger.getLogger("").log(Level.SEVERE, null, e);
                }
            }
        }
    }
    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String building = cmbBuilding.getValue();
        String faculty = cmbFaculty.getValue();
        String center = cmbCenter.getValue();
        String level = cmbLevel.getValue();
        String department = cmbDepartment.getValue();
        String lName = txtLname.getText();
        String emId = txtLemid.getText();
        String rank = txtRank.getText();

        LecturerTM selectedItem = tblLecturer.getSelectionModel().getSelectedItem();
        try {
            addLecturerBO.updateLecturer(new AddLecturerDTO(
                    selectedItem.getId(),
                    emId,
                    lName,
                    department,
                    faculty,
                    center,
                    building,
                    level,
                    rank
            ));

            selectedItem.setEmpId(txtLemid.getText());
            selectedItem.setLastName(txtLname.getText());
            selectedItem.setRank(txtRank.getText());
            selectedItem.setBuildingNo(building);
            selectedItem.setFaculty(faculty);
            selectedItem.setCenter(center);
            selectedItem.setLevel(level);
            selectedItem.setDepartment(department);

            tblLecturer.refresh();

            new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Something went wrong").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }
    }

    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            Parent root1 = null;

            FXMLLoader fxmlLoader = null;
            switch (icon.getId()) {
                case "iconHome":
                    root1 = FXMLLoader.load(this.getClass().getResource("/lk/sliit/itpmproject/view/MainForm.fxml"));
                    break;
                case "iconStudent":
                    root1 = FXMLLoader.load(this.getClass().getResource("/lk/sliit/itpmproject/view/AddStudent.fxml"));
                    break;
                case "iconLocation":
                    root1 = FXMLLoader.load(this.getClass().getResource("/lk/sliit/itpmproject/view/AddRBLocation.fxml"));
                    break;
                case "iconLecture":
                    root1 = FXMLLoader.load(this.getClass().getResource("/lk/sliit/itpmproject/view/AddLecturer.fxml"));
                    break;
                default:
                    fxmlLoader = new FXMLLoader(this.getClass().getResource("/lk/sliit/itpmproject/view/AddWorkingDaysAndHours.fxml"));
                    root1 = fxmlLoader.load();
                    break;
            }

            if (root1 != null) {
                Scene subScene = new Scene(root1);
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

}
