package domainapp.dom.country;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        table = "states",
        schema = "country"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findStatesForCountry", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.country.State "
                        + "WHERE referredCountryID.indexOf(:referredCountryID) >= 0 ")

})

@javax.jdo.annotations.Unique(name="State_id_UNQ", members = {"referredCountryID", "stateID"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)


public class State implements Comparable <State>{

	//region > title
    public TranslatableString title() {
        return TranslatableString.tr("States of : {name}", "name", getReferredCountryID());
    }
    //endregion
    
    //region > constructor
    public static final int COUNTRYID_LENGTH = 7;
    @javax.jdo.annotations.Column(allowsNull = "false", length=COUNTRYID_LENGTH)
    private String referredCountryID;
    public State(final String referredCountryID) {
        this.referredCountryID = referredCountryID;
    }

    // Getter method for country ID
    public String getReferredCountryID() {
        return referredCountryID;
    }
    
    public void setReferredCountryID(String referredCountryID) {
        this.referredCountryID = referredCountryID;
    }
    
    // end region 
    
    //region > state name (editable property)
    public static final int STATENAME_LENGTH = 40;

    public static class StateNameDomainEvent extends PropertyDomainEvent<State,String> {}
    @javax.jdo.annotations.Column(allowsNull = "true", length = STATENAME_LENGTH)
    @Property(
            domainEvent = StateNameDomainEvent.class,
            regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*"
    )

    private String stateName;
    
    public String getStateName() {
        return stateName;
    }
    public void setStateName(final String firstName) {
        this.stateName = firstName;
    }    
    //endregion

    //region > state id (editable property)
    public static final int STATEID_LENGTH = 40;

    public static class StateIDDomainEvent extends PropertyDomainEvent<State,String> {}
    @javax.jdo.annotations.Column(allowsNull = "false", length = STATEID_LENGTH)
    @Property(
            domainEvent = StateIDDomainEvent.class,
            regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*"
    )

    private String stateID;
    
    public String getStateID() {
        return stateID;
    }
    public void setStateID(final String uniqueID) {
        this.stateID = uniqueID;
    }    
    //endregion

    
    @Override
	public int compareTo(final State other) {
		// TODO Auto-generated method stub
        return ObjectContracts.compare(this, other, "name");		
	}
	
}
