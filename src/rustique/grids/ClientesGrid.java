package rustique.grids;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rustique.controllers.ClientesController;
import rustique.RustiqueParameters;

public class ClientesGrid implements RustiqueParameters {

    private static ClientesController thisController = null;
    private static ClientesGrid thisClientesGrid = null;

    private GridPane grid;

    /**
     * Patron Singleton
     * @return instancia unica de clase
     */
    public static ClientesGrid getInstance() {
        if(thisClientesGrid == null)
            thisClientesGrid = new ClientesGrid();
        return thisClientesGrid;
    }

    /**
     * Constructor de clase
     */
    private ClientesGrid() {
        thisController = ClientesController.getInstance();

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(vPadding, hPadding, vPadding, hPadding));

        Button nuevoCliente = new Button("Nuevo cliente");
        nuevoCliente.setPrefSize(buttonsWidth, buttonsHeight);
        nuevoCliente.setStyle(buttonsStyle);
        nuevoCliente.setOnAction(e -> thisController.actionPerformed("nuevo cliente"));

        Button borrarCliente = new Button("Borrar cliente");
        borrarCliente.setPrefSize(buttonsWidth, buttonsHeight);
        borrarCliente.setStyle(buttonsStyle);
        borrarCliente.setOnAction(e -> thisController.actionPerformed("borrar cliente"));

        grid.add(nuevoCliente, 0, 0);
        grid.add(borrarCliente, 0, 1);
    }

    /**
     * Retorna el grid para que las vistas lo utilicen
     * @return objeto nodo GridPane
     */
    public GridPane getGridPane() {
        return this.grid;
    }

    /**
     * Seteo de posicion de la grid
     * @param x layout x
     * @param y layout y
     */
    public void setLayout(double x, double y) {
        this.grid.setLayoutX(x);
        this.grid.setLayoutY(y);
    }
}
