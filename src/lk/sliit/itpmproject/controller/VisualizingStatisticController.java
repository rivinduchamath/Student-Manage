package lk.sliit.itpmproject.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.sliit.itpmproject.business.BOFactory;
import lk.sliit.itpmproject.business.BOTypes;
import lk.sliit.itpmproject.business.custom.StudentStaticsBO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VisualizingStatisticController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private BarChart<Integer, Integer> barChartX;

    @FXML
    private Label lblRLect;

    @FXML
    private Label lblRStudent;

    @FXML
    private Label lblRegiSubject;

    @FXML
    private Label lblRegiRooms;

    @FXML
    private Label lblLLecturer;

    @FXML
    private Label lblLSubject;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    @FXML
    private Label lblLGroup;

    private final StudentStaticsBO staticsBO = BOFactory.getInstance().getBO(BOTypes.STUDENT_STATICS);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series set1;
        XYChart.Series set2;
        set1 = new XYChart.Series<>();
         set2 = new XYChart.Series<>();
        set1.setName("LecturerRooms");
        set2.setName("Laboratories");

        int labCount = 0;
        try {
            labCount = staticsBO.findLabCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int labLectureCount = 0;
        try {
            labLectureCount = staticsBO.findLecturerHallCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

        set2.getData().add(new XYChart.Data("LecturerRooms ", labLectureCount));
        set1.getData().add(new XYChart.Data("Laboratories ", labCount));
        barChartX.getData().addAll(set1);
        barChartX.getData().addAll(set2);


        try {
            int registeredLecturers = staticsBO.findLectureCount();

            lblRLect.setText(String.valueOf(registeredLecturers));

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Something went wrong").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }
        try {
            int studentCount = staticsBO.findStudentCount();

            lblRStudent.setText(String.valueOf(studentCount));

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Something went").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }
        try {
            int subjectCount = staticsBO.findSubjhectCount();

            lblRegiSubject.setText(String.valueOf(subjectCount));

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Something went ").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }
        try {
            int regisRoom = staticsBO.findRegisteredRoomCount();
            lblRegiRooms.setText(String.valueOf(regisRoom));

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Something wrong").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }

        try {
            String latestLecturer = staticsBO.findLatestLecturer();
            lblLLecturer.setText((latestLecturer));

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "went wrong").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }
        try {
            String latestGroup = staticsBO.findLatestGroup();
            lblLGroup.setText((latestGroup));

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, " went wrong").show();
            Logger.getLogger("").log(Level.SEVERE, null, e);
        }
        try {
            String lastSubject = staticsBO.findLatestSubject();
            lblLSubject.setText((lastSubject));

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
