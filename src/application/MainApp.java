package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import view.*;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        Tab tabCurso = new Tab("Cursos");
        tabCurso.setClosable(false);
        tabCurso.setContent(new CursoView());

        Tab tabDisciplina = new Tab("Disciplinas");
        tabDisciplina.setClosable(false);
        tabDisciplina.setContent(new DisciplinaView());

        Tab tabProfessor = new Tab("Professores");
        tabProfessor.setClosable(false);
        tabProfessor.setContent(new ProfessorView());

        Tab tabInscricao = new Tab("Inscricoes");
        tabInscricao.setClosable(false);
        tabInscricao.setContent(new InscricaoView());

        Tab tabConsultaInscritos = new Tab("Consulta Inscritos");
        tabConsultaInscritos.setClosable(false);
        tabConsultaInscritos.setContent(new ConsultaInscritos());

        Tab tabProcessosAbertos = new Tab("Processos Abertos");
        tabProcessosAbertos.setClosable(false);
        tabProcessosAbertos.setContent(new ConsultaProcessosAbertos());

        tabPane.getTabs().addAll(tabCurso, tabDisciplina, tabProfessor,
                tabInscricao, tabConsultaInscritos, tabProcessosAbertos);

        Scene scene = new Scene(tabPane, 800, 600);

        primaryStage.setTitle("Sistema de Contratacao Docente");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}