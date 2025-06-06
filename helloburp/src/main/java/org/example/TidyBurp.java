package org.example;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.proxy.ProxyHttpRequestResponse;
import org.example.logtable.LogTableController;
import org.example.logtable.LogTableModel;
//import burp.api.montoya.ui.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TidyBurp implements BurpExtension {
    public static MontoyaApi montoya;

    @Override
    public void initialize(MontoyaApi montoyaApi) {
        this.montoya = montoyaApi;
        montoyaApi.extension().setName("TidyBurp");
        montoyaApi.logging().logToOutput("TidyBurp");

//        ExtensionUI ui = new ExtensionUI();
//        montoyaApi.userInterface().registerSuiteTab("BAEME", ui.getUi());
//        List<ProxyHttpRequestResponse> history = montoyaApi.proxy().history();
//        List<List<Object>> data = new ArrayList<>();
//        for (ProxyHttpRequestResponse hItem : history) {
//            HttpRequest req = hItem.request();
//            data.add(Arrays.asList(req.method(), req.url(), req.pathWithoutQuery(), req.query()));
//        }
        LogTableController controller = new LogTableController();
        LogTableModel logTableModel = controller.getLogTableModel();
        LogProcessor processor = new LogProcessor();
        montoyaApi.http().registerHttpHandler(new LogHTTPHandler(controller, processor));
        TBContextMenu contextMenu = new TBContextMenu(controller); // might need LTC?
        montoyaApi.userInterface().registerContextMenuItemsProvider(contextMenu);

        AnnotationsTab annotationsTab = new AnnotationsTab(controller, montoya);
        montoyaApi.userInterface().registerSuiteTab(annotationsTab.name(), annotationsTab.getPanel());
    }
}
