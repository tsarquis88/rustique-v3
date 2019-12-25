package rustique.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rustique.Main;
import rustique.MessagesManager;
import rustique.RustiqueParameters;
import rustique.models.Cliente;
import rustique.models.Modelo;
import rustique.models.Obra;

public class CambiarModeloDialog implements RustiqueParameters {

    private Dialog<ButtonType> dialog;
    private Modelo viejoModelo;

    private TextField nombre;

    private TextField saldo;
    private TextArea comentarios;

    private TextField precio;
    private TextField autor;
    private TextField tipo;
    private TextField tamanio;

    /**
     * Constructor de la clase
     * @param viejoModelo modelo a modificar
     */
    public CambiarModeloDialog(Modelo viejoModelo) {
        this.viejoModelo = viejoModelo;
        dialog = new Dialog<>();
        String titulo = "";

        GridPane grid = new GridPane();
        grid.setHgap(hPadding);
        grid.setVgap(vPadding);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        int stop = 0;
        if(viejoModelo instanceof Cliente) {
            titulo = "Modificar cliente";
            stop = 6;

            nombre = new TextField();
            nombre.setPromptText(viejoModelo.getDatos().get(0));
            nombre.setText(viejoModelo.getDatos().get(1));
            grid.add(nombre, 9, 0);

            saldo = new TextField();
            saldo.setPromptText(viejoModelo.getDatos().get(2));
            saldo.setText(viejoModelo.getDatos().get(3));
            grid.add(saldo, 9, 2);

            comentarios = new TextArea();
            comentarios.setPromptText(viejoModelo.getDatos().get(4));
            comentarios.setText(viejoModelo.getDatos().get(5));
            comentarios.setPrefSize(120, 50);
            comentarios.setWrapText(true);
            grid.add(comentarios, 9, 4);
        }
        else if(viejoModelo instanceof Obra) {
            titulo = "Modificar obra";
            stop = 10;

            nombre = new TextField();
            nombre.setPromptText(viejoModelo.getDatos().get(0));
            nombre.setText(viejoModelo.getDatos().get(1));
            grid.add(nombre, 9, 0);

            autor = new TextField();
            autor.setPromptText(viejoModelo.getDatos().get(2));
            autor.setText(viejoModelo.getDatos().get(3));
            grid.add(autor, 9, 2);

            precio = new TextField();
            precio.setPromptText(viejoModelo.getDatos().get(4));
            precio.setText(viejoModelo.getDatos().get(5));
            grid.add(precio, 9, 4);

            tipo = new TextField();
            tipo.setPromptText(viejoModelo.getDatos().get(6));
            tipo.setText(viejoModelo.getDatos().get(7));
            grid.add(tipo, 9, 6);

            tamanio = new TextField();
            tamanio.setPromptText(viejoModelo.getDatos().get(8));
            tamanio.setText(viejoModelo.getDatos().get(9));
            grid.add(tamanio, 9, 8);
        }

        dialog.setTitle(titulo);
        dialog.setHeaderText("");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        for(int row = 0; row < stop; row = row + 2) {

            Label tipoDatoActual = new Label(viejoModelo.getDatos().get(row) + " actual: ");
            tipoDatoActual.setStyle("-fx-font-weight: bold;");
            grid.add(tipoDatoActual, 0, row);

            Label datoActual = new Label(viejoModelo.getDatos().get(row + 1));
            grid.add(datoActual, 1, row);

            Label tipoDatoNuevo = new Label(viejoModelo.getDatos().get(row) + " nuevo: ");
            tipoDatoNuevo.setStyle("-fx-font-weight: bold;");
            grid.add(tipoDatoNuevo, 8, row);
        }

        dialog.getDialogPane().setContent(grid);
    }

    /**
     * Muestra de dialogo
     */
    public void show() {
        Platform.runLater(() -> dialog.getDialogPane().getScene().getWindow().sizeToScene());

        this.dialog.showAndWait();
    }


    /**
     * Retorno de modelo cambiado
     * @return objeto Modelo nuevo
     */
    public Modelo getResult() {

        if(dialog.getResult() == ButtonType.OK) {
            if(this.viejoModelo instanceof Cliente) {
                Cliente cliente = new Cliente();
                cliente.setNombre(this.nombre.getText());
                cliente.setId(((Cliente) viejoModelo).getId());

                if (this.saldo.getText() == null || !Main.isNumeroValido(this.saldo.getText()))
                    cliente.setSaldo(0);
                else
                    cliente.setSaldo(Main.safeDecode(this.saldo.getText()));

                if (comentarios.getText() == null)
                    cliente.setComentarios("");
                else if (comentarios.getText().length() > comentMaxSize) {
                    cliente.setComentarios("");
                    MessagesManager.showInformationAlert("Comentario muy largo, " +
                            "se puso en blanco");
                } else
                    cliente.setComentarios(this.comentarios.getText());

                return cliente;
            }
            else if(this.viejoModelo instanceof Obra) {
                Obra obra = new Obra();
                obra.setNombre(this.nombre.getText());
                obra.setAutor(this.autor.getText());
                obra.setTipo(this.tipo.getText());
                obra.setTamanio(this.tamanio.getText());
                obra.setHasImage(((Obra) viejoModelo).getHasImage());
                obra.setId(((Obra) viejoModelo).getId());

                if(this.precio.getText() == null || !Main.isNumeroValido(this.precio.getText()))
                    obra.setPrecio(0);
                else
                    obra.setPrecio(Main.safeDecode(this.precio.getText()));

                return obra;
            }
        }
        return null;
    }
}