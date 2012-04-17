package org.jboss.as.console.client.shared.subsys.messaging.forms;

import com.google.gwt.user.client.ui.Widget;
import org.jboss.as.console.client.shared.help.FormHelpPanel;
import org.jboss.as.console.client.shared.subsys.Baseadress;
import org.jboss.as.console.client.shared.subsys.messaging.model.Bridge;
import org.jboss.as.console.client.shared.viewframework.builder.FormLayout;
import org.jboss.as.console.client.widgets.forms.FormToolStrip;
import org.jboss.ballroom.client.widgets.forms.CheckBoxItem;
import org.jboss.ballroom.client.widgets.forms.Form;
import org.jboss.ballroom.client.widgets.forms.PasswordBoxItem;
import org.jboss.ballroom.client.widgets.forms.TextAreaItem;
import org.jboss.ballroom.client.widgets.forms.TextBoxItem;
import org.jboss.ballroom.client.widgets.forms.TextItem;
import org.jboss.dmr.client.ModelNode;

/**
 * @author Heiko Braun
 * @date 4/3/12
 */
public class BridgeConnectionsForm {


    private Form<Bridge> form = new Form<Bridge>(Bridge.class);
    private FormToolStrip.FormCallback<Bridge> callback;
    private boolean provideTools = true;
    private boolean isCreate = false;

    public BridgeConnectionsForm(FormToolStrip.FormCallback<Bridge> callback) {
        this.callback = callback;
        form.setNumColumns(2);
    }

    public BridgeConnectionsForm(FormToolStrip.FormCallback<Bridge> callback, boolean provideTools) {
        this.callback = callback;
        this.provideTools = provideTools;
        form.setNumColumns(2);
    }

    public void setIsCreate(boolean b) {
        this.isCreate = b;
    }

    public Widget asWidget() {


        TextBoxItem queueName = new TextBoxItem("queueName", "Queue Name");
        TextBoxItem forward = new TextBoxItem("forwardingAddress", "Forward Address");
        TextAreaItem filter = new TextAreaItem("filter", "Filter", false);
        TextAreaItem transformer = new TextAreaItem("transformerClass", "Transformer Class", false);

        TextBoxItem user = new TextBoxItem("user", "User", false);
        PasswordBoxItem pass = new PasswordBoxItem("password", "Password", false);

        CheckBoxItem failoverInitial = new CheckBoxItem("failoverInitial", "Failover Initial?");
        CheckBoxItem failoverShutdown = new CheckBoxItem("failoverShutdown", "Failover Shutdown?");

        CheckBoxItem started = new CheckBoxItem("started", "Started?");


        if(isCreate) {

            TextBoxItem name = new TextBoxItem("name", "Name");

            form.setFields(
                    name, queueName,
                    forward);
        }
        else
        {
            TextItem name = new TextItem("name", "Name");

            form.setFields(
                    name, started,
                    queueName, forward,
                    filter,transformer,
                    user, pass,
                    failoverInitial, failoverShutdown
            );
        }

        FormHelpPanel helpPanel = new FormHelpPanel(
                new FormHelpPanel.AddressCallback() {
                    @Override
                    public ModelNode getAddress() {
                        ModelNode address = Baseadress.get();
                        address.add("subsystem", "messaging");
                        address.add("hornetq-server", "*");
                        address.add("bridge", "*");
                        return address;
                    }
                }, form);

        FormToolStrip<Bridge> formTools = new FormToolStrip<Bridge>(form, callback);

        FormLayout formLayout = new FormLayout()
                .setForm(form)
                .setHelp(helpPanel);

        if(provideTools)
            formLayout.setSetTools(formTools);

        return formLayout.build();
    }

    public Form<Bridge> getForm() {
        return form;
    }
}
