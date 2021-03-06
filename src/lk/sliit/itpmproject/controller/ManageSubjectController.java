package lk.sliit.itpmproject.controller;

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
import lk.sliit.itpmproject.business.custom.AddSubjectBO;
import lk.sliit.itpmproject.dto.AddSubjectDTO;
import lk.sliit.itpmproject.util.SubjectTM;

public class ManageSubjectController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<SubjectTM> tblSubject;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private CheckBox chSem1;

    @FXML
    private CheckBox chSem2;

    @FXML
    private TextField txtSubjectName;

    @FXML
    private TextField txtSubCode;

    @FXML
    private Spinner<Integer> spinOfferedYear;

    @FXML
    private Spinner<Integer> spinLecHours;

    @FXML
    private Spinner<Integer> spinTuteHours;

    @FXML
    private Spinner<Integer> spinLabHours;

    @FXML
    private Spinner<Integer> spinEvaHours;

    private final AddSubjectBO addSubjectBO = BOFactory.getInstance().getBO(BOTypes.ADD_SUBJECT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblSubject.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblSubject.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("offeredYear"));
        tblSubject.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("semester1"));
        tblSubject.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("semester2"));
        tblSubject.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("noOfLecHrs"));
        tblSubject.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("noOfTutHrs"));
        tblSubject.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("noOfLabHrs"));
        tblSubject.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("subName"));
        tblSubject.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("noOfEvlHrs"));
        tblSubject.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("subCode"));

        try {
            List<AddSubjectDTO> addSubjectDTOList = addSubjectBO.findAllSubjects();
            ObservableList<SubjectTM> subjectTMS = tblSubject.getItems();
            for (AddSubjectDTO addSubjectDTO : addSubjectDTOList
            ) {
                subjectTMS.add(new SubjectTM(
                        addSubjectDTO.getId(),
                        addSubjectDTO.getOffredYear(),
                        addSubjectDTO.isSemester1(),
                        addSubjectDTO.isSemester2(),
                        addSubjectDTO.getNoOfLecHrs(),
                        addSubjectDTO.getNoOfTutHrs(),
                        addSubjectDTO.getNoOflabHrs(),
                        addSubjectDTO.getSubName(),
                        addSubjectDTO.getNoOfEvlHrs(),
                        addSubjectDTO.getSubCode()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tblSubject.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            SubjectTM selectedItem = tblSubject.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                btnDelete.setDisable(true);
                return;
            }

            btnDelete.setDisable(false);

            int spinYear = selectedItem.getOfferedYear();
            int spinLHours = selectedItem.getNoOfLecHrs();
            int spinTHours = selectedItem.getNoOfTutHrs();
            int spinLbHours = selectedItem.getNoOfLabHrs();
            int spinEvalHours = selectedItem.getNoOfEvlHrs();
            txtSubjectName.setText(selectedItem.getSubName());
            txtSubCode.setText(selectedItem.getSubCode());

            if (selectedItem.isSemester1()) {
                chSem1.setSelected(true);
            } else {
                chSem1.setSelected(false);
            }
            if (selectedItem.isSemester2()) {
                chSem2.setSelected(true);
            } else {
                chSem2.setSelected(false);
            }

            SpinnerValueFactory<Integer> valueFactory1 =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, spinYear);
            spinOfferedYear.setValueFactory(valueFactory1);

            SpinnerValueFactory<Integer> valueFactory2 =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, spinLHours);
            spinLecHours.setValueFactory(valueFactory2);

            SpinnerValueFactory<Integer> valueFactory3 =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, spinTHours);
            spinTuteHours.setValueFactory(valueFactory3);

            SpinnerValueFactory<Integer> valueFactory4 =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, spinLbHours);
            spinLabHours.setValueFactory(valueFactory4);

            SpinnerValueFactory<Integer> valueFactory5 =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, spinEvalHours);
            spinEvaHours.setValueFactory(valueFactory5);
        });
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to clear?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        txtSubjectName.setText("");
        txtSubCode.setText("");
        chSem1.setSelected(false);
        chSem2.setSelected(false);

        SpinnerValueFactory<Integer> spinnerValueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);
        SpinnerValueFactory<Integer> spinnerValueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        SpinnerValueFactory<Integer> spinnerValueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        SpinnerValueFactory<Integer> spinnerValueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        SpinnerValueFactory<Integer> spinnerValueFactory5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        this.spinOfferedYear.setValueFactory(spinnerValueFactory1);
        this.spinLecHours.setValueFactory(spinnerValueFactory2);
        this.spinTuteHours.setValueFactory(spinnerValueFactory3);
        this.spinLabHours.setValueFactory(spinnerValueFactory4);
        this.spinEvaHours.setValueFactory(spinnerValueFactory5);

        spinOfferedYear.setEditable(false);
        spinLecHours.setEditable(false);
        spinTuteHours.setEditable(false);
        spinLabHours.setEditable(false);
        spinEvaHours.setEditable(false);
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Detail?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        boolean vaa = buttonType.isPresent();
        if (vaa) {
            ButtonType bt = buttonType.get();
            if (bt == ButtonType.YES) {
                SubjectTM selectedItem = tblSubject.getSelectionModel().getSelectedItem();
                try {
                    addSubjectBO.deleteItem(selectedItem.getId());
                    tblSubject.getItems().remove(selectedItem);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.INFORMATION, "Something went wrong").show();
                    Logger.getLogger("").log(Level.SEVERE, null, e);
                }
            }
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        int spinYear = spinOfferedYear.getValue();
        int spinLHours = spinLecHours.getValue();
        int spinTHours = spinTuteHours.getValue();
        int spinLbHours = spinLabHours.getValue();
        int spinEvalHours = spinEvaHours.getValue();

        boolean sem1 = chSem1.isSelected();
        boolean sem2 = chSem2.isSelected();

        SubjectTM selectedItem = tblSubject.getSelectionModel().getSelectedItem();
        try {
            addSubjectBO.updateSubject(new AddSubjectDTO(
                    selectedItem.getId(),
                    spinYear,
                    sem1,
                    sem2,
                    spinLHours,
                    spinTHours,
                    spinLbHours,
                    txtSubjectName.getText(),
                    spinEvalHours,
                    txtSubCode.getText()
            ));

            selectedItem.setNoOfEvlHrs(spinEvalHours);
            selectedItem.setNoOfLabHrs(spinLbHours);
            selectedItem.setNoOfLecHrs(spinLHours);
            selectedItem.setNoOfTutHrs(spinTHours);
            selectedItem.setOfferedYear(spinYear);
            selectedItem.setSemester1(sem1);
            selectedItem.setSemester2(sem2);
            selectedItem.setSubCode(txtSubCode.getText());
            selectedItem.setSubName(txtSubjectName.getText());
            tblSubject.refresh();
            new Alert(Alert.AlertType.INFORMATION, "Updated Successfully").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Something went wrong").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }
    }

    @FXML
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

