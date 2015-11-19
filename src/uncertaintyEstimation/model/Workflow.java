package uncertaintyEstimation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by fykos on 19/11/15.
 */
public class Workflow {
    private final StringProperty workflow;
    private Source sourceObj;

    public Workflow(String workflow, Source sourceObj){
        this.workflow = new SimpleStringProperty(workflow);
        this.sourceObj = sourceObj;
    }

    public String getWorkflow(){
        return workflow.get();
    }

    public void setWorkflow(String workflow){
        this.workflow.set(workflow);
    }

    public StringProperty workflowProperty(){
        return workflow;
    }
}
