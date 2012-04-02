package org.jboss.as.console.client.shared.subsys.messaging;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.jboss.as.console.client.shared.subsys.messaging.model.ConnectionFactory;
import org.jboss.as.console.client.shared.viewframework.builder.MultipleToOneLayout;
import org.jboss.ballroom.client.widgets.tables.DefaultCellTable;

/**
 * @author Heiko Braun
 * @date 4/2/12
 */
public class ConnectionFactoryList {


    private HTML serverName;
    private DefaultCellTable<ConnectionFactory> factoryTable;
    private ListDataProvider<ConnectionFactory> factoryProvider;
    private MsgDestinationsPresenter presenter;

    public ConnectionFactoryList(MsgDestinationsPresenter presenter) {
        this.presenter = presenter;
    }

    Widget asWidget() {


        serverName = new HTML("Replace me");
        serverName.setStyleName("content-header-label");

        factoryTable = new DefaultCellTable<ConnectionFactory>(10);
        factoryProvider = new ListDataProvider<ConnectionFactory>();
        factoryProvider.addDataDisplay(factoryTable);

        Column<ConnectionFactory, String> nameColumn = new Column<ConnectionFactory, String>(new TextCell()) {
            @Override
            public String getValue(ConnectionFactory object) {
                return object.getName();
            }
        };

        Column<ConnectionFactory, String> jndiColumn = new Column<ConnectionFactory, String>(new TextCell()) {
            @Override
            public String getValue(ConnectionFactory object) {
                return object.getJndiName();
            }
        };

        factoryTable.addColumn(nameColumn, "Name");
        factoryTable.addColumn(jndiColumn, "JNDI");

        MultipleToOneLayout layout = new MultipleToOneLayout()
                .setPlain(true)
                .setHeadlineWidget(serverName)
                .setMaster("Connection Factories", factoryTable)
                .addDetail("Detail 1", new HTML())
                .addDetail("Detail 1", new HTML());

        return layout.build();
    }
}
