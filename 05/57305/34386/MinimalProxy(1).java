import org.apache.jmeter.exceptions.IllegalUserActionException;
import org.apache.jmeter.gui.tree.JMeterTreeModel;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.protocol.http.proxy.ProxyControl;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.ListedHashTree;

import java.io.IOException;

public class MinimalProxy {
    public static void main(String args[]) throws IOException, IllegalUserActionException {
        // Without these, no ResourceBundle is made, and you get an NPE
        // at org.apache.jmeter.util.JMeterUtils.getResStringDefault(JMeterUtils.java:505)
        JMeterUtils.setJMeterHome("/opt/jmeter"); // Or wherever you put it.
        JMeterUtils.loadJMeterProperties(JMeterUtils.getJMeterBinDir() + "/jmeter.properties");
        JMeterUtils.initLocale();

        TestPlan testPlan = new TestPlan();
        ThreadGroup threadGroup = new ThreadGroup();
        ListedHashTree testPlanTree = new ListedHashTree();
        testPlanTree.add(testPlan);
        testPlanTree.add(threadGroup, testPlan);

        JMeterTreeModel treeModel = new JMeterTreeModel(new Object());
        ProxyControl.setGlobalTreeRoot(treeModel);
        JMeterTreeNode root = (JMeterTreeNode) treeModel.getRoot();
        treeModel.addSubTree(testPlanTree, root);

        ProxyControl proxy = new ProxyControl();
        proxy.setTarget(treeModel.getNodeOf(threadGroup));

        treeModel.addComponent(proxy, (JMeterTreeNode) root.getChildAt(1));

        proxy.startProxy();
    }
}
