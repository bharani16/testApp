package domainapp.dom.country;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Country.class
)
@DomainServiceLayout(
        named = "Customers",
        menuOrder = "10"
)

public class CountryMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Country> listAll() {
        return countryMenu.listAll();
    }
	
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Country> findByName(
            @ParameterLayout(named="Country Name")
            final String countryName
    ) {
        return countryMenu.findByName(countryName);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<CountryMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public Country create(
    		@ParameterLayout(named="Country ID")
            final String countryID
            ) {
    	Country country = countryMenu.create(countryID);
        return country;
    }


    @javax.inject.Inject
    CountryRepository countryMenu;
}
