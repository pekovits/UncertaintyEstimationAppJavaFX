package uncertaintyEstimation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by fykos on 19/11/15.
 */
public class Source {
    private final StringProperty source;
    private final StringProperty type;
    private final StringProperty condition;
    private final StringProperty distribution;
    private final StringProperty assumptions;


    /**
     * Constructor with some initial data
     * @param source
     * @param type
     */
    public Source(String source, String type){
        this.source = new SimpleStringProperty(source);
        this.type = new SimpleStringProperty(type);

        this.condition = new SimpleStringProperty("some condition");
        this.distribution = new SimpleStringProperty("some distributions");
        this.assumptions = new SimpleStringProperty("many assumptions");
    }

    /**
     * Default constructor
     */
    public Source(){
        this(null, null);
    }

    // Getters and setters for source field
    public String getSource(){
        return source.get();
    }

    public void setSource(String source){
        this.source.set(source);
    }

    public StringProperty sourceProperty(){
        return source;
    }

    // Getters and setters for type field
    public String getType(){
        return type.get();
    }

    public void setType(String type){
        this.type.set(type);
    }

    public StringProperty typeProperty(){
        return type;
    }

    // Getter and setter for condition
    public String getCondition(){
        return condition.get();
    }

    public void setCondition(String condition){
        this.condition.set(condition);
    }

    public StringProperty conditionProperty(){
        return condition;
    }

    // Getter and setter for distribution
    public String getDistribution(){
        return distribution.get();
    }

    public void setDistribution(String distribution){
        this.distribution.set(distribution);
    }

    public StringProperty distributionProperty(){
        return distribution;
    }

    // Getter and setter for assumptions
    public String getAssumptions(){
        return assumptions.get();
    }

    public void setAssumptions(String assumptions){
        this.assumptions.set(assumptions);
    }

    public StringProperty assumptionsProperty(){
        return  assumptions;
    }


}
