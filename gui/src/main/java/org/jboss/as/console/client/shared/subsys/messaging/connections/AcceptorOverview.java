package org.jboss.as.console.client.shared.subsys.messaging.connections;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.as.console.client.shared.subsys.messaging.model.Acceptor;
import org.jboss.as.console.client.shared.subsys.messaging.model.AcceptorType;
import org.jboss.as.console.client.widgets.ContentDescription;

import java.util.List;

/**
 * @author Heiko Braun
 * @date 4/4/12
 */
public class AcceptorOverview {

    private HTML serverName;
    private MsgConnectionsPresenter presenter;
    private AcceptorList genericAcceptors;
    private AcceptorList remoteAcceptors;
    private AcceptorList invmAcceptors;

    public AcceptorOverview(MsgConnectionsPresenter presenter) {
        this.presenter = presenter;
    }

    Widget asWidget() {

        LayoutPanel layout = new LayoutPanel();

        VerticalPanel panel = new VerticalPanel();
        panel.setStyleName("rhs-content-panel");

        ScrollPanel scroll = new ScrollPanel(panel);
        layout.add(scroll);

        layout.setWidgetTopHeight(scroll, 0, Style.Unit.PX, 100, Style.Unit.PCT);

        // ---

        serverName = new HTML("Replace me");
        serverName.setStyleName("content-header-label");

        panel.add(serverName);
        panel.add(new ContentDescription("Defines a way in which connections can be made to the HornetQ server."));

        // ----

        TabPanel bottomLayout = new TabPanel();
        bottomLayout.addStyleName("default-tabpanel");


        genericAcceptors = new AcceptorList(presenter, AcceptorType.GENERIC);
        remoteAcceptors = new AcceptorList(presenter, AcceptorType.REMOTE);
        invmAcceptors = new AcceptorList(presenter, AcceptorType.INVM);

        bottomLayout.add(remoteAcceptors.asWidget(),"Remote");
        bottomLayout.add(invmAcceptors.asWidget(),"In-VM");
        bottomLayout.add(genericAcceptors.asWidget(),"Generic");

        bottomLayout.selectTab(0);

        panel.add(bottomLayout);

        return layout;
    }

    public void setGenericAcceptors(List<Acceptor> list) {
        serverName.setText("Acceptors: Provider "+ presenter.getCurrentServer());
        genericAcceptors.setAcceptors(list);
    }

    public void setRemoteAcceptors(List<Acceptor> remote) {
        remoteAcceptors.setAcceptors(remote);
    }

    public void setInvmAcceptors(List<Acceptor> invm) {
        invmAcceptors.setAcceptors(invm);
    }
}
