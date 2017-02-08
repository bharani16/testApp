package domainapp.dom.country;

import java.util.Set;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        table = "country",
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
                name = "findCountryByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.country.Country "
                        + "WHERE countryName.indexOf(:countryName) >= 0 ")

})

@javax.jdo.annotations.Unique(name="Country_id_UNQ", members = {"countryID"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)


public class Country implements Comparable<Country>{

	
    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Country: {name}", "name", getCountryName());
    }
    //endregion
    
    //region > constructor
    public Country(final String borrowerID) {
        setCountryID(borrowerID);
    }
    //endregion

    //region > Country ID (read-only property)
    public static final int CID_LENGTH = 7;

    @javax.jdo.annotations.Column(
            allowsNull="false",
            length = CID_LENGTH
    )
    private String countryID;
    @Property(
            editing = Editing.DISABLED
    )
  
    public String getCountryID() {
        return countryID;
    }
    public void setCountryID(final String borrowerID) {
        this.countryID = borrowerID;
    }
    //endregion

    //region > country name (editable property)
    public static final int COUNTRYNAME_LENGTH = 40;

    public static class CountryNameDomainEvent extends PropertyDomainEvent<Country,String> {}
    @javax.jdo.annotations.Column(allowsNull = "true", length = COUNTRYNAME_LENGTH)
    @Property(
            command = CommandReification.ENABLED,
            publishing = Publishing.ENABLED,
            domainEvent = CountryNameDomainEvent.class,
            regexPattern = "\\w[@&:\\-\\,\\.\\+ \\w]*"
    )

    private String countryName;
    
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(final String firstName) {
        this.countryName = firstName;
    }    
    //endregion

    //region > family member details (property), add (action), remove (action)
    @javax.jdo.annotations.Join(table="states", column="referredCountryID")
    @javax.jdo.annotations.Element(column="countryID")
    private Set<State> states = new TreeSet<>();
    @Collection()public Set<State> getStates() {
        return states;
    }

    public void setStates(final Set<State> states) {
        this.states = states;
    }

    public void addToStates(final State states) {
        getStates().add(states);
    }
    
    public void removeFromStates(final State states) {
        getStates().remove(states);
    }
    
	@Override
	public int compareTo(final Country other) {
		// TODO Auto-generated method stub
        return ObjectContracts.compare(this, other, "name");		
	}
}
