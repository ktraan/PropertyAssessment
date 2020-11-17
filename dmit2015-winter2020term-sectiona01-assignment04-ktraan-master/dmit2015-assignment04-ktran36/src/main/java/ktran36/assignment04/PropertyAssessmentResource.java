package ktran36.assignment04;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.PropertyAssessment;




/**
 * 
 * This class will retrieve data from the DB
 * 
 * @author Kevin Tran
 * @version 2020-04-08
 *
 * 1. A method that returns a list of ward names sorted ascendingly
 * 2. A method that returns a list of sorted negihbourhood names for a given ward
 * 3. A method that returns a list of sorted street names for that neighbourhood
 * 4. A method that returns a list of PropertyAssessment entity sorted by
 *    house number, for a given neighbourhood name and a given street name
 * 5. A method that returns a single PropertyAssessment entity for a given account number
 * 6. A method that returns a single PropertyAssessment entity for a given street name and house number
 * 7. Returns a collection of PropertyAssessment entities for given:
 * latitude, longtidude, distance from the latitute and longitude. Return all PropertyAssessments
 * within given distance 
 * 8. A method that returns the ward name and total assessment value of each ward
 * 9. A method that returns the neihgbourhood name and total assessment value of each neighbourhood for a given ward
 * 10. A method that returns assessment class and total assessment value of each assessment class for ag iven ward
 * 
 * URI												Http Method		Description
   -------------------------------------------		-----------		------------------------------------
   /pa/wards										GET
    curl -i http://localhost:8080/webapi/pa/wards
    
   /pa/neighbourhoods-by-ward/{ward}				GET
    curl -i http://localhost:8080/webapi/pa/neighbourhoods-by-ward/1
    
   /pa/street-by-neighbourhood/{neighbourhood}		GET
   	curl -i http://localhost:8080/webapi/pa/street-by-neighbourhood/CHAMBERY
   	curl -i http://localhost:8080/webapi/pa/street-by-neighbourhood/cham
   	curl -i http://localhost:8080/webapi/pa/street-by-neighbourhood/bell
    
   /pa/assessments/{neighbourhood}/{streetName}		GET
    curl -i http://localhost:8080/webapi/pa/assessments/chambery/castle
    curl -i http://localhost:8080/webapi/pa/assessments/cham/cas
    curl -i http://localhost:8080/webapi/pa/assessments/belle/16
    
   /pa/assessment/{accountNumber}					GET
    curl -i http://localhost:8080/webapi/pa/assessment/10000000
  
   /pa/assessment/{streetName}/{houseNumber}		GET
    curl -i http://localhost:8080/webapi/pa/assessment/107A/100000
    
   /pa/assessments/distance/{latitude}/{longitude}/{distance}	GET
    
   /pa/ward-total-assessment						GET
    curl -i http://localhost:8080/webapi/pa/ward-total-assessment
    
   /pa/neighbourhood-total-assessment/{ward}		GET
   curl -i http://localhost:8080/webapi/pa/neighbourhood-total-assessment/1
    
   /pa/assessment-total-assessment-class-by-ward/{ward}	 	GET
   curl -i http://localhost:8080/webapi/pa/assessment-total-assessment-class-by-ward/1

 *
 *
 */


@Path("pa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PropertyAssessmentResource 
{
	@PersistenceContext(unitName = "mssql-jpa-pu")
	private EntityManager entityManager;
	
	
	// 1. A method that returns a list of ward names sorted ascendingly
	//  curl -i http://localhost:8080/webapi/pa/wards
	@GET
	@Path("wards")
	public Response findWards()
	{
		try {
		List<String> resultList = entityManager.createQuery(
				"SELECT p.ward FROM PropertyAssessment p "
				+ "GROUP BY p.ward "
				+ "ORDER BY CAST(SUBSTRING(ward,5,3) as int) ", String.class)
				.getResultList();
			return Response.ok(resultList).build();
		}catch (Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}
	
	
	// 2. A method that returns a list of sorted negihbourhood names for a given ward
	// curl -i http://localhost:8080/webapi/pa/neighbourhoods-by-ward/1
	@GET
	@Path("neighbourhoods-by-ward/{ward}")
	public Response findNeighbourhoodsByWard(@PathParam("ward") String wardName)
	{
		try {
		List<String> resultList = entityManager.createQuery(
				  "SELECT DISTINCT p.neighbourhood "
				+ "FROM PropertyAssessment p "
				+ "WHERE p.ward LIKE 'Ward ' + :wardNumber "
				+ "ORDER BY p.neighbourhood", String.class)
				.setParameter("wardNumber", wardName)
				.getResultList();
			return Response.ok(resultList).build();
		}catch (Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}
	
	
	// 3. A method that returns a list of sorted street names for that neighbourhood
	// pa/street-by-neighbourhood/{neighbourhood}
	@GET
	@Path("/street-by-neighbourhood/{neighbourhood}")
	public Response findStreetNamesByNeighbourhood(
		@PathParam("neighbourhood") String neighbourhood)
	{
		try {
		List<String> resultList = entityManager.createQuery(
				  "SELECT p.streetName FROM PropertyAssessment p "
				+ "WHERE p.neighbourhood LIKE :neighbourhoodName + '%' "
				+ "GROUP BY p.streetName "
				+ "ORDER BY p.streetName ", String.class)
				.setParameter("neighbourhoodName", neighbourhood)
				.getResultList();
			return Response.ok(resultList).build();
		}catch (Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}
	
	// 4. A method that returns a list of PropertyAssessment entity sorted by
	//    house number, for a given neighbourhood name and a given street name
	// /pa/assessments/{name}/{streetName}
	
	@GET
	@Path("assessments/{neighbourhood}/{streetName}")
	public Response findAssessmentsByNeighbourhoodAndStreet(
			@PathParam("neighbourhood") String neighbourhood,
			@PathParam("streetName") String streetName)
	{
		try {
		List<PropertyAssessment> resultList = entityManager.createQuery(
				  "SELECT p FROM PropertyAssessment p "
				  + "WHERE p.neighbourhood LIKE :neighbourhoodName + '%' "
				  + "AND p.streetName LIKE + :street + '%' "
				  + "ORDER BY p.houseNumber", PropertyAssessment.class)
				.setParameter("neighbourhoodName", neighbourhood)
				.setParameter("street", streetName)
				.getResultList();
			return Response.ok(resultList).build();
		}catch (Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}
	
	// 5. A method that returns a single PropertyAssessment entity for a given account number
	// /pa/assessment/{accountNumber}
	@GET
	@Path("assessment/{accountNumber}")
	public Response findSingleAssessmentByAccountNumber(
			@PathParam("accountNumber") String accountNumber)
	{
		try
		{
			PropertyAssessment property = entityManager.createQuery(
					"SELECT p FROM PropertyAssessment p "
					+ "WHERE p.accountNumber = :accountNumber"
					, PropertyAssessment.class)
					.setParameter("accountNumber", accountNumber)
					.getSingleResult();
					return Response.ok(property).build();
		}catch (NoResultException ex) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}catch (Exception ex){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
 //  6. A method that returns a single PropertyAssessment entity
 //	    for a given street name and house number
 //  	/pa/assessment/{streetName}/{houseNumber}
	@GET
	@Path("assessment/{streetName}/{houseNumber}")
	public Response findSingleAssessmentByStreetAndNumber(
			@PathParam("streetName") String streetName,
			@PathParam("houseNumber") String houseNumber)
	{
		try
		{
			PropertyAssessment property = entityManager.createQuery(
					"SELECT p FROM PropertyAssessment p "
					+ "WHERE p.streetName LIKE :streetName + '%' "
					+ "AND p.houseNumber = :houseNumber "
					, PropertyAssessment.class)
					.setParameter("streetName", streetName)
					.setParameter("houseNumber", houseNumber)
					.getSingleResult();
					return Response.ok(property).build();
		}catch (NoResultException ex) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}catch (Exception ex){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
//	7. Returns a collection of PropertyAssessment entities for given:
//	 * latitude, longtidude, distance from the latitute and longitude. Return all PropertyAssessments
//	 * within given distance 
	// /pa/assessments/distance/{latitude}/{longitude}/{distance}
//	@GET
//	@Path("assessments/distance/{latitude}/{longitude}/{distance}")
//	public Response assessmentsByGeoLocation(
//			@PathParam("latitude") double latitude,
//			@PathParam("longitude") double longitude,
//			@PathParam("distance") double distance)
//	{
//		
//	}
	
	
	// 8. A method that returns the ward name and total assessment value of each ward
	// /pa/ward-total-assessment
	// curl -i http://localhost:8080/webapi/pa/ward-total-assessment
	@GET
	@Path("ward-total-assessment")
	public Response totalAssessment()
	{
		try {
			List<Tuple> resultList = entityManager.createQuery(
					"SELECT p.ward as Ward, SUM(p.assessedValue) as TotalAssessment "
					+ "FROM PropertyAssessment p "
					+ "GROUP BY p.ward "
					+ "ORDER BY CAST(SUBSTRING(p.ward, 5, 3) as int), SUM(p.assessedValue) DESC", Tuple.class)
					.getResultList();
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			resultList.forEach(tuple -> {
				JsonObject jsonObject = Json.createObjectBuilder()
						.add("ward", tuple.get("Ward", String.class))
						.add("totalAssessment", tuple.get("TotalAssessment", Long.class))
						.build();
				jsonArrayBuilder.add(jsonObject);
			});
			return Response.ok(jsonArrayBuilder.build()).build();
			
		}catch(Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}
	
	 // 9. A method that returns the neihgbourhood name 
	//  and total assessment value of each neighbourhood for a given ward
	//  pa/neighbourhood-total-assessment/{ward}
	// curl -i http://localhost:8080/webapi/pa/neighbourhood-total-assessment/1
	@GET
	@Path("neighbourhood-total-assessment/{ward}")
	public Response totalAssessmentByWard(@PathParam("ward") String ward)
	{
		try {
			List<Tuple> resultList = entityManager.createQuery(
					"SELECT p.neighbourhood as Neighbourhood, "
					+ "SUM(p.assessedValue) as TotalAssessment "
					+ "FROM PropertyAssessment p "
					+ "WHERE p.ward LIKE 'Ward ' + :wardNumber "
					+ "GROUP BY p.neighbourhood "
					+ "ORDER BY SUM(p.assessedValue) ", Tuple.class)
					.setParameter("wardNumber", ward)
					.getResultList();
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			resultList.forEach(tuple -> {
				JsonObject jsonObject = Json.createObjectBuilder()
						.add("neighbourhood", tuple.get("Neighbourhood", String.class))
						.add("totalAssessment", tuple.get("TotalAssessment", Long.class))
						.build();
				jsonArrayBuilder.add(jsonObject);
			});
			return Response.ok(jsonArrayBuilder.build()).build();
			
		}catch(Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}

	 // 10. A method that returns assessment class and total assessment value of each assessment class for a given ward
	//  /pa/assessment-total-by-ward/{ward}	
	//  curl -i http://localhost:8080/webapi/pa/assessment-total-assessment-class-by-ward/1
	@GET
	@Path("assessment-total-assessment-class-by-ward/{ward}")
	public Response totalAssessmentAndClassByWard(@PathParam("ward") String ward)
	{
		try {
			List<Tuple> resultList = entityManager.createQuery(
					"SELECT p.assessmentClass as AssessmentClass, "
					+ "SUM(p.assessedValue) as TotalAssessment "
					+ "FROM PropertyAssessment p "
					+ "WHERE p.ward LIKE 'Ward ' + :wardNumber "
					+ "GROUP BY p.assessmentClass "
					+ "ORDER BY p.assessmentClass ", Tuple.class)
					.setParameter("wardNumber", ward)
					.getResultList();
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			resultList.forEach(tuple -> {
				JsonObject jsonObject = Json.createObjectBuilder()
						.add("assessmentClass", tuple.get("AssessmentClass", String.class))
						.add("totalAssessment", tuple.get("TotalAssessment", Long.class))
						.build();
				jsonArrayBuilder.add(jsonObject);
			});
			return Response.ok(jsonArrayBuilder.build()).build();
			
		}catch(Exception ex)
		{
			return Response.serverError().entity(ex.getMessage()).build();
		}
	}
	
	
	
	
	
}
