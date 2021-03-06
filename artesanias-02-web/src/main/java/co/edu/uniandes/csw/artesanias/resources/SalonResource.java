/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.artesanias.resources;

import co.edu.uniandes.csw.artesanias.dtos.ConferenciaDTO;
import co.edu.uniandes.csw.artesanias.dtos.PabellonDTO;
import co.edu.uniandes.csw.artesanias.dtos.SalonDTO;
import co.edu.uniandes.csw.artesanias.dtos.detail.SalonDetailDTO;
import co.edu.uniandes.csw.artesanias.ejbs.ConferenciaLogic;
import co.edu.uniandes.csw.artesanias.ejbs.PabellonLogic;
import co.edu.uniandes.csw.artesanias.ejbs.SalonLogic;
import co.edu.uniandes.csw.artesanias.entities.ConferenciaEntity;
import co.edu.uniandes.csw.artesanias.entities.PabellonEntity;
import co.edu.uniandes.csw.artesanias.entities.SalonEntity;
import co.edu.uniandes.csw.artesanias.exceptions.BusinessLogicException;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.util.ReflectionHelper;

/**
 * @author IVAN
 */
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
@Path("/salones")
public class SalonResource
{
	@Inject
	private SalonLogic logic;
        
        @Inject
        private ConferenciaLogic logicConferencia;
        
        @Inject
        private PabellonLogic pabellonLogic;
	
	@Context
	private HttpServletResponse response;
	
	private List<SalonDTO> listEntity2DTO( List<SalonEntity> entityList )
	{
		List<SalonDTO> list = new LinkedList<>( );
		for( SalonEntity entity : entityList )
		{
			list.add( new SalonDTO( entity ) );
		}
		return list;
	}
	
	@GET
	public List<SalonDTO> getSalones( @PathParam( "pabellonId" ) Long pabellonId )throws BusinessLogicException
	{
//            if (SalonLogic.checkPabellon(pabellonId)==null) {
//                throw new WebApplicationException("No existe el pabellon", 404);
//                   
//            }
		return listEntity2DTO( logic.getSalonesFromPabellon( pabellonId ) );
	}
	
	@GET
	@Path( "{id: \\d+}" )
        
	public SalonDTO getSalon( @PathParam( "id" ) Long id,  @PathParam( "pabellonId") Long pabellonId )
	{
		return new SalonDTO( logic.getSalonFrompabellon(id,pabellonId ) );
	}
	
	@POST
	public SalonDTO createSalon( @PathParam( "pabellonId" )
			                             Long pabellonId, SalonDetailDTO dto ) throws BusinessLogicException
	{
             if (logic.checkPabellon(pabellonId,dto.getId())==null) {
                throw new WebApplicationException("No existe el pabellon", 404);
                   
            }
		PabellonEntity en = new PabellonEntity( );
		en.setId( pabellonId );
		dto.setPabellon( new PabellonDTO( en ) );
                dto.getPabellon();
		SalonEntity entity = logic.createSalon( dto.toEntity( ) );
		return new SalonDTO( entity );
	}
	
	@PUT
	@Path( "{id: \\d+}" )
	public SalonDTO updateSalon(
			@PathParam( "pabellonId" ) Long pabellonId,
			@PathParam( "id" ) Long id, SalonDTO dto ) throws BusinessLogicException
	{
            if (logic.checkPabellon(pabellonId,dto.getId())==null) {
                throw new WebApplicationException("No existe el pabellon", 404);
                   
            }
		PabellonEntity pab = new PabellonEntity( );
		pab.setId( pabellonId );
		SalonEntity entity = dto.toEntity( );
		entity.setId( id );
		entity.setPabellon( pab );
		return new SalonDTO( logic.updateSalon( entity ) );
	}
	
	@DELETE
	@Path( "{id: \\d+}" )
	public void deleteSalon( @PathParam( "id" ) Long idSalon,@PathParam( "pabellonId" ) Long pabellonId )
	{
            if (logic.checkPabellon(pabellonId,idSalon)==null) {
                throw new WebApplicationException("No existe el pabellon", 404);
                   
            }
		logic.deleteSalon( idSalon );
	}
        
        
	
	@Path( "{salonId: \\d+}/conferencias" )
	public ConferenciaSalonResource getConferenciaFromSalonResource(@PathParam( "id" ) Long idSalon )
	{
           
            
		return new ConferenciaSalonResource();
	}
        
//        @Path( "{salonId: \\d+}" )
//	public Class<ConferenciaResource> getConferenciaFromFeriaResource( )
//	{
//		return ConferenciaResource.class;
//	}
        
}
