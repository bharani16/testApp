package domainapp.dom.country;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Country.class
)

public class CountryRepository {

	public List<Country> listAll() {
        return repositoryService.allInstances(Country.class);
    }

    public List<Country> findByName(final String countryName) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Country.class,
                        "findCuountryByName",
                        "countryName", countryName));
    }

    public Country create(final String countryID) {
        Country object = new Country(countryID);
        
        serviceRegistry.injectServicesInto(object);
    	serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;

}
