package org.jboss.as.console.client.standalone.runtime;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.as.console.client.core.NameTokens;
import org.jboss.as.console.client.shared.model.SubsystemRecord;
import org.jboss.as.console.client.widgets.nav.Predicate;
import org.jboss.ballroom.client.layout.LHSTreeSection;
import org.jboss.ballroom.client.layout.LHSNavTree;
import org.jboss.ballroom.client.layout.LHSNavTreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Braun
 * @date 11/2/11
 */
public class StandaloneRuntimeNavigation {

    private VerticalPanel stack;
    private VerticalPanel layout;

    private List<SubsystemRecord> subsystems;

    private List<Predicate> metricPredicates = new ArrayList<Predicate>();
    private List<Predicate> runtimePredicates = new ArrayList<Predicate>();

    private ScrollPanel scroll;
    private TreeItem metricLeaf;
    private TreeItem runtimeLeaf;
    private LHSNavTree navigation;

    public Widget asWidget()
    {
        layout = new VerticalPanel();
        layout.setStyleName("fill-layout-width");

        stack = new VerticalPanel();
        stack.setStyleName("fill-layout-width");

        // ----------------------------------------------------


        navigation = new LHSNavTree("standalone-runtime");
        navigation.getElement().setAttribute("aria-label", "Runtime Tasks");

        // ----------------------------------------------------

        TreeItem serverLeaf = new LHSTreeSection("Server Status", true);

        LHSNavTreeItem server = new LHSNavTreeItem("Configuration", NameTokens.StandaloneServerPresenter);
        LHSNavTreeItem jvmItem = new LHSNavTreeItem("JVM", NameTokens.VirtualMachine);
        serverLeaf.addItem(server);
        serverLeaf.addItem(jvmItem);

        navigation.addItem(serverLeaf);


        // -------------

        metricLeaf = new LHSTreeSection("Subsystem Metrics");


        LHSNavTreeItem datasources = new LHSNavTreeItem("Datasources", "ds-metrics");
        LHSNavTreeItem jmsQueues = new LHSNavTreeItem("JMS Destinations", "jms-metrics");
        LHSNavTreeItem web = new LHSNavTreeItem("Web", "web-metrics");
        LHSNavTreeItem tx = new LHSNavTreeItem("Transactions", "tx-metrics");
        LHSNavTreeItem jpa = new LHSNavTreeItem("JPA", NameTokens.JPAMetricPresenter);

        metricPredicates.add(new Predicate("datasources", datasources));
        metricPredicates.add(new Predicate("messaging", jmsQueues));
        metricPredicates.add(new Predicate("web", web));
        metricPredicates.add(new Predicate("transactions", tx));
        metricPredicates.add(new Predicate("jpa", jpa));

        navigation.addItem(metricLeaf);


        // ---

        runtimeLeaf = new LHSTreeSection("Runtime Operations");


        LHSNavTreeItem osgi = new LHSNavTreeItem("OSGi", NameTokens.OSGiRuntimePresenter);

        runtimePredicates.add(new Predicate("osgi", osgi));

        navigation.addItem(runtimeLeaf);

        // ----------------------------------------------------

        TreeItem deploymentLeaf = new LHSTreeSection("Deployments");

        deploymentLeaf.addItem(new LHSNavTreeItem("Manage Deployments", NameTokens.DeploymentListPresenter));
        deploymentLeaf.addItem(new LHSNavTreeItem("Webservices", NameTokens.WebServiceRuntimePresenter));

        navigation.addItem(deploymentLeaf);

        // ---
        stack.add(navigation);
        layout.add(stack);

        scroll = new ScrollPanel(layout);

        navigation.expandTopLevel();

        return scroll;
    }



    public void setSubsystems(List<SubsystemRecord> subsystems) {


        metricLeaf.removeItems();
        runtimeLeaf.removeItems();

        // match subsystems
        for(SubsystemRecord subsys : subsystems)
        {

            for(Predicate predicate : metricPredicates)
            {
                if(predicate.matches(subsys.getKey()))
                    metricLeaf.addItem(predicate.getNavItem());
            }

            for(Predicate predicate : runtimePredicates)
            {
                if(predicate.matches(subsys.getKey()))
                    runtimeLeaf.addItem(predicate.getNavItem());
            }
        }

        navigation.expandTopLevel();

    }


}
