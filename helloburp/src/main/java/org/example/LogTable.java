package org.example;

import javax.swing.*;
import java.awt.*;

public class LogTable extends JTable {
    private LogTableController controller;

    LogTable(LogTableController controller) {
        super(controller.getLogTableModel(), controller.getLogTableColumnModel());
        this.setTableHeader(controller.getTableHeader());
        this.controller = controller;

        this.getSelectionModel().addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()) return;
            RequestViewerController requestViewerController = this.controller.getRequestViewerController();
            int selectedRow = getSelectedRow();
            if(selectedRow == -1){
                requestViewerController.setDisplayedEntity(null);
            }else {
                // Use a relative instead of an absolute index (This prevents an issue when a filter is set)
                LogEntry logEntry = controller.getLogTableModel().getRow(selectedRow);
                if (logEntry != null) {
                    requestViewerController.setDisplayedEntity(logEntry);
                }
            }
        });
    }
}
