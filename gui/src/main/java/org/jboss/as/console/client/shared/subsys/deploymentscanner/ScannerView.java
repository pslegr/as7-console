/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.jboss.as.console.client.shared.subsys.deploymentscanner;

import org.jboss.as.console.client.Console;
import org.jboss.as.console.client.shared.subsys.deploymentscanner.model.DeploymentScanner;
import org.jboss.as.console.client.shared.viewframework.AbstractEntityView;
import org.jboss.as.console.client.shared.viewframework.Columns.EnabledColumn;
import org.jboss.as.console.client.shared.viewframework.Columns.NameColumn;
import org.jboss.as.console.client.shared.viewframework.EntityAttributes;
import org.jboss.as.console.client.shared.viewframework.EntityToDmrBridge;
import org.jboss.ballroom.client.widgets.forms.Form;
import org.jboss.ballroom.client.widgets.forms.FormAdapter;
import org.jboss.ballroom.client.widgets.tables.DefaultCellTable;



/**
 * Main view class for Deployment Scanners.  This class assembles the editor and reacts to 
 * FrameworkView callbacks.
 * 
 * @author Stan Silvert
 */
public class ScannerView extends AbstractEntityView<DeploymentScanner> implements ScannerPresenter.MyView {

    private EntityToDmrBridge scannerBridge;
    private EntityAttributes attributes;

    @Override
    protected EntityAttributes getEntityAttributes() {
        return this.attributes;
    }

    @Override
    protected EntityToDmrBridge getEntityBridge() {
        return this.scannerBridge;
    }

    @Override
    protected String getPluralEntityName() {
        return Console.CONSTANTS.subsys_deploymentscanner_scanners();
    }

    @Override
    protected FormAdapter<DeploymentScanner> makeAddEntityForm() {
        Form<DeploymentScanner> form = new Form(DeploymentScanner.class);
        form.setNumColumns(1);
        form.setFields(attributes.findAttribute("name").getItemForAdd(),
                       attributes.findAttribute("path").getItemForAdd(),
                       attributes.findAttribute("relativeTo").getItemForAdd(),
                       attributes.findAttribute("enabled").getItemForAdd());
        return form;
    }

    @Override
    protected DefaultCellTable<DeploymentScanner> makeEntityTable() {
        DefaultCellTable<DeploymentScanner> table = new DefaultCellTable<DeploymentScanner>(4);
        
        table.addColumn(new NameColumn(), NameColumn.LABEL);
        table.addColumn(new EnabledColumn(), EnabledColumn.LABEL);
        
        return table;
    }
  
    /**
     * Called when Presenter is created.
     * @param bridge The EntityToDmrBridge for DeploymentScanner
     */
    @Override
    public void setEntityToDmrBridge(EntityToDmrBridge bridge) {
        this.scannerBridge = bridge;
        this.attributes = bridge.getEntityAttributes();
    }
    
}