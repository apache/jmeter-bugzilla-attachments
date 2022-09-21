/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.jmeter.control.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.jmeter.control.Controller;
import org.apache.jmeter.control.ModuleController;
import org.apache.jmeter.control.TestFragmentController;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.action.ActionNames;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.MenuFactory;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.layout.VerticalLayout;

/**
 * ModuleController Gui.
 *
 */
public class ModuleControllerGui extends AbstractControllerGui implements ActionListener
// implements UnsharedComponent
{
	private static final String SEPARATOR = " > ";

    private static final long serialVersionUID = 240L;

    private JMeterTreeNode selected = null;
    
    private final DefaultTreeModel mcTreeModel;
    
    private final JTree mcTreeNodes;

    private final JLabel warningLabel;

    private JButton expandButton;

    /**
     * Initializes the gui panel for the ModuleController instance.
     */
    public ModuleControllerGui() {
		mcTreeModel = new DefaultTreeModel(new DefaultMutableTreeNode());
		mcTreeNodes = new JTree(mcTreeModel);
		
		//test element name replacer (called after selecting module to run in module controller tree)
		MouseAdapter ml = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				TreePath selPath = mcTreeNodes.getPathForLocation(e.getX(),
						e.getY());
				if (selPath != null) {
					Object[] nodesOnPath = selPath.getPath();
					//do not allow to reference test plan
					if (nodesOnPath.length > 1) {						
						JMeterTreeNode tn = (JMeterTreeNode) ((DefaultMutableTreeNode)selPath.getLastPathComponent()).getUserObject();
						//prevent also changing name if thread group is selected
						if(!(tn.getTestElement() instanceof AbstractThreadGroup)){
							setElementName("[MC]"+tn.getName());
						}
					}
				}
			}
		};
		mcTreeNodes.addMouseListener(ml);
		mcTreeNodes.setCellRenderer(new ModuleControllerCellRenderer());
		
        warningLabel = new JLabel(""); // $NON-NLS-1$
        init();
    }
	
	private void setElementName(String name)
	{
		this.namePanel.setName(name);
	}

    /** {@inheritDoc}} */
    @Override
    public String getLabelResource() {
        return "module_controller_title"; // $NON-NLS-1$
    }
    /** {@inheritDoc}} */
    @Override
    public void configure(TestElement el) {
        super.configure(el);
        ModuleController controller = (ModuleController) el;
        this.selected = controller.getSelectedNode();
        if (selected == null && controller.getNodePath() != null) {
            warningLabel.setText(JMeterUtils.getResString("module_controller_warning") // $NON-NLS-1$
                    + renderPath(controller.getNodePath()));
        } else {
            warningLabel.setText(""); // $NON-NLS-1$
        }
        reinitialize();
    }

    private String renderPath(Collection<?> path) {
        Iterator<?> iter = path.iterator();
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        while (iter.hasNext()) {
            if (first) {
                first = false;
                iter.next();
                continue;
            }
            buf.append(iter.next());
            if (iter.hasNext()) {
                buf.append(SEPARATOR); // $NON-NLS-1$
            }
        }
        return buf.toString();
    }

    /** {@inheritDoc}} */
    @Override
    public TestElement createTestElement() {
        ModuleController mc = new ModuleController();
        configureTestElement(mc);
        if (selected != null) {
            mc.setSelectedNode(selected);
        }
        return mc;
    }

    /** {@inheritDoc}} */
    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element); 
        JMeterTreeNode tn = null;
        DefaultMutableTreeNode lastSelected = (DefaultMutableTreeNode) this.mcTreeNodes.getLastSelectedPathComponent(); 
        if(lastSelected != null && lastSelected.getUserObject() instanceof JMeterTreeNode){
        	tn = (JMeterTreeNode) lastSelected.getUserObject();
        }
        if(tn != null){
            selected = tn;
            //prevent from selecting thread group or test plan elements
            if (selected != null 
            		&& !(selected.getTestElement() instanceof AbstractThreadGroup)
            		&& !(selected.getTestElement() instanceof TestPlan))
            {
                ((ModuleController) element).setSelectedNode(selected);
            }
        }
    }

    /** {@inheritDoc}} */
    @Override
    public void clearGui() {
        super.clearGui();

        //nodes.setSelectedIndex(-1);
        selected = null;
    }


    /** {@inheritDoc}} */
    @Override
    public JPopupMenu createPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        JMenu addMenu = MenuFactory.makeMenus(
                new String[] {
                        MenuFactory.CONFIG_ELEMENTS,
                        MenuFactory.ASSERTIONS,
                        MenuFactory.TIMERS,
                        MenuFactory.LISTENERS,
                },
                JMeterUtils.getResString("add"),  // $NON-NLS-1$
                ActionNames.ADD);
        menu.add(addMenu);
        MenuFactory.addEditMenu(menu, true);
        MenuFactory.addFileMenu(menu);
        return menu;
    }

    private void init() {
        setLayout(new VerticalLayout(5, VerticalLayout.BOTH, VerticalLayout.TOP));
        setBorder(makeBorder());
        add(makeTitlePanel());

        JPanel modulesPanel = new JPanel();
        
        expandButton = new JButton(JMeterUtils.getResString("expand")); //$NON-NLS-1$
        expandButton.addActionListener(this);
        modulesPanel.add(expandButton);
        modulesPanel.setLayout(new BoxLayout(modulesPanel, BoxLayout.Y_AXIS));;
        modulesPanel.add(Box.createRigidArea(new Dimension(0,5)));
        
        JLabel nodesLabel = new JLabel(JMeterUtils.getResString("module_controller_module_to_run")); // $NON-NLS-1$
        modulesPanel.add(nodesLabel);
        modulesPanel.add(warningLabel);		
        add(modulesPanel);
        
        JPanel treePanel = new JPanel();
        treePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		treePanel.add(mcTreeNodes);
        add(treePanel);
    }
    
	private TreeNode[] findPathInTreeModel(int level, TreeNode[] testPlanPath, DefaultMutableTreeNode root)
	{
		if(level >= testPlanPath.length)
			return null;
		int childCount = root.getChildCount();		
		JMeterTreeNode searchedTreeNode = (JMeterTreeNode) testPlanPath[level];

		for (int i = 0; i < childCount; i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);			 
			JMeterTreeNode childUserObj = (JMeterTreeNode) child.getUserObject();

			if(!childUserObj.equals(searchedTreeNode)){
				continue;
			} else {
				if(level == (testPlanPath.length - 1)){
					return child.getPath();					
				} else {
					return findPathInTreeModel(level+1, testPlanPath, child);
				}
			}
		}
		return null;
	}
	
	private void focusSelectedOnTree(JMeterTreeNode selected, TreeNode[] path)
	{
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) mcTreeNodes.getModel().getRoot();
		TreeNode[] dmtnPath = this.findPathInTreeModel(1, path, root);
		if (dmtnPath != null) {
			TreePath treePath = new TreePath(dmtnPath);
			mcTreeNodes.setSelectionPath(treePath);
			mcTreeNodes.scrollPathToVisible(treePath);
		}
	}
    
	private void reinitialize() {
		((DefaultMutableTreeNode) mcTreeModel.getRoot()).removeAllChildren();

		GuiPackage gp = GuiPackage.getInstance();
		JMeterTreeNode root;
		if (gp != null) {
			root = (JMeterTreeNode) GuiPackage.getInstance().getTreeModel().getRoot();
			buildTreeNodeModel(root, 0, null);
			mcTreeModel.nodeStructureChanged((TreeNode) mcTreeModel.getRoot());
		}
		if (selected != null) {
			TreeNode[] path = selected.getPath();
			TreeNode[] filteredPath = new TreeNode[path.length-1];
			
			//ignore first element of path - WorkBench, (why WorkBench is appearing in the path ???)
			for(int i = 1; i < path.length; i++){
				filteredPath[i-1] = path[i];
			}
			
			//set focus on element in tree
			this.focusSelectedOnTree(selected, filteredPath);
		}
	}

	private void buildTreeNodeModel(JMeterTreeNode node, int level,
			DefaultMutableTreeNode parent) {

		if (node != null) {
			for (int i = 0; i < node.getChildCount(); i++) {
				JMeterTreeNode cur = (JMeterTreeNode) node.getChildAt(i);
				TestElement te = cur.getTestElement();

				if (te instanceof Controller
						&& !(te instanceof ModuleController) && level > 0) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(cur);
					parent.add(newNode);
					buildTreeNodeModel(cur, level + 1, newNode);
					
				} else if (te instanceof TestFragmentController) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(cur);
					parent.add(newNode);
					buildTreeNodeModel(cur, level + 1, newNode);
				
				} else if (te instanceof AbstractThreadGroup) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(cur);
					parent.add(newNode);
					buildTreeNodeModel(cur, level + 1, newNode);
				}

				else if (te instanceof TestPlan) {
					((DefaultMutableTreeNode) mcTreeModel.getRoot())
							.setUserObject(cur);
					buildTreeNodeModel(cur, level,
							(DefaultMutableTreeNode) mcTreeModel.getRoot());
				}
			}
		}
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==expandButton) {
            JMeterTreeNode tn = null;
            DefaultMutableTreeNode selected = (DefaultMutableTreeNode) this.mcTreeNodes.getLastSelectedPathComponent(); 
            if(selected != null && selected.getUserObject() instanceof JMeterTreeNode){
            	tn = (JMeterTreeNode) selected.getUserObject();
            }
            if(tn != null){
				TreePath treePath = new TreePath(tn.getPath());
				GuiPackage.getInstance().getTreeListener().getJTree()
						.setSelectionPath(treePath);
				GuiPackage.getInstance().getTreeListener().getJTree()
						.scrollPathToVisible(treePath);
            }
        } 
    }


    /**
     * @param selected JMeterTreeNode tree node to expand
     */
    protected void expandToSelectNode(JMeterTreeNode selected) {
        GuiPackage guiInstance = GuiPackage.getInstance();
        JTree jTree = guiInstance.getMainFrame().getTree();
        jTree.expandPath(new TreePath(selected.getPath()));
        selected.setMarkedBySearch(true);
    }
    
    private class ModuleControllerCellRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 1129098620102526299L;

		public ModuleControllerCellRenderer() {
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int row, boolean p_hasFocus) {
            JMeterTreeNode node = (JMeterTreeNode)((DefaultMutableTreeNode) value).getUserObject();
            if(node != null){
	            super.getTreeCellRendererComponent(tree, node.getName(), sel, expanded, leaf, row,
	                    p_hasFocus);
	            ImageIcon ic = node.getIcon(true);
	            if (ic != null) {
                    setIcon(ic);
	            }
            }
	        return this;
        }
    }
}
