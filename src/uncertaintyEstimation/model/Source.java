package uncertaintyEstimation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by fykos on 19/11/15.
 */
public class Source {
    private String workflow;
    private String source;
    private String type;
    private String condition;
    private String distribution;
    private String assumptions;



    /**
     * Constructor with some initial data
     * @param source
     * @param type
     */
    public Source(String workflow, String source, String type){
        this.workflow = workflow;
        this.source = source;
        this.type = type;

        this.condition = "some condition";
        this.distribution = "some distributions";
        this.assumptions = "many assumptions";
    }

    /**
     * Default constructor
     */
    public Source(){
        this(null, null, null);
    }

    // Getters and setters for source field
    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    // Getters and setters for type field
    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }


    // Getter and setter for condition
    public String getCondition(){
        return condition;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

    // Getter and setter for distribution
    public String getDistribution(){
        return distribution;
    }

    public void setDistribution(String distribution){
        this.distribution = distribution;
    }


    // Getter and setter for assumptions
    public String getAssumptions(){
        return assumptions;
    }

    public void setAssumptions(String assumptions){
        this.assumptions = assumptions;
    }

    // Getter and setter for workflow
    public String getWorkflow(){
        return workflow;
    }

    public void setWorkflow(String workflow){
        this.workflow = workflow;
    }


}
