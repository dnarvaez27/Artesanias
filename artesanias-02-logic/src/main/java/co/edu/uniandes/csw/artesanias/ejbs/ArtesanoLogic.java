/*
 * The MIT License
 *
 * Copyright 2017 d.narvaez11.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.csw.artesanias.ejbs;

import co.edu.uniandes.csw.artesanias.entities.ArtesanoEntity;
import co.edu.uniandes.csw.artesanias.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.artesanias.persistence.ArtesanoPersistence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author d.narvaez11
 */

@Stateless
public class ArtesanoLogic
{
	@Inject
	private ArtesanoPersistence persistence;
	
	/**
	 * Creates an Artesano in the Data Base
	 *
	 * @param entity Objet from ArtesanoEntity with the new data.
	 * @return Objet from ArtesanoEntity with the new data and ID.
	 */
	public ArtesanoEntity createArtesano( ArtesanoEntity entity ) throws BusinessLogicException
	{
		checkNNValues( entity );
		if( existsWithIdentificacion( entity.getIdentificacion( ) ) )
		{
			//TODO Test
			throw new BusinessLogicException( "Ya existe un artesano con la identificacion dada", Response.Status.FORBIDDEN );
		}
		return persistence.create( entity );
	}
	
	private boolean existsWithIdentificacion( String identificacion )
	{
		List<ArtesanoEntity> artesanos = persistence.findAll( );
		for( ArtesanoEntity artesano : artesanos )
		{
			if( artesano.getIdentificacion( ).equals( identificacion ) )
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retrieves the list of the registers of Artesano.
	 *
	 * @return Collection of objects of ArtesanoEntity.
	 */
	public List<ArtesanoEntity> getArtesanos( )
	{
		return persistence.findAll( );
	}
	
	/**
	 * Retrieves the data of an instance of Artesano by its ID.
	 *
	 * @param id Identifier of the instance to consult.
	 * @return Instance of ArtesanoEntity with the data from the Artesano consulted.
	 * @throws BusinessLogicException en caso que no exista un artesano con el ID dado (404 NOT FOUND)
	 */
	public ArtesanoEntity getArtesano( Long id ) throws BusinessLogicException
	{
		ArtesanoEntity en = persistence.find( id );
		if( en != null )
		{
			return en;
		}
		throw new BusinessLogicException( String.format( "No existe el artesano con el id %s", id ), Response.Status.NOT_FOUND );
	}
	
	/**
	 * Updates the information from an instance of Artesano.
	 *
	 * @param entity Instance of ArtesanoEntity with the new data.
	 * @return Instance of ArtesanoEntity with the updated data.
	 */
	public ArtesanoEntity updateArtesano( ArtesanoEntity entity ) throws BusinessLogicException
	{
		ArtesanoEntity info = getArtesano( entity.getId( ) );
		
		boolean nombre = entity.getNombre( ) == null || entity.getNombre( ).isEmpty( );
		boolean telefono = entity.getTelefono( ) == null || entity.getTelefono( ).isEmpty( );
		boolean ciudad = entity.getCiudad( ) == null;
		boolean ident = entity.getIdentificacion( ) == null || entity.getIdentificacion( ).isEmpty( );
		boolean imagen = entity.getImage( ) == null || entity.getImage( ).isEmpty( );
		boolean artesanias = entity.getArtesanias( ) == null || entity.getArtesanias( ).isEmpty( );
		boolean reviews = entity.getReviews( ) == null || entity.getReviews( ).isEmpty( );
		boolean ferias = entity.getFerias( ) == null || entity.getFerias( ).isEmpty( );
		
		entity.setNombre( nombre ? info.getNombre( ) : entity.getNombre( ) );
		entity.setIdentificacion( ident ? info.getIdentificacion( ) : entity.getIdentificacion( ) );
		entity.setTelefono( telefono ? info.getTelefono( ) : entity.getTelefono( ) );
		entity.setCiudad( ciudad ? info.getCiudad( ) : entity.getCiudad( ) );
		entity.setImage( imagen ? info.getImage( ) : entity.getImage( ) );
		entity.setReviews( reviews ? info.getReviews( ) : entity.getReviews( ) );
		entity.setArtesanias( artesanias ? info.getArtesanias( ) : entity.getArtesanias( ) );
		entity.setFerias( ferias ? info.getFerias( ) : entity.getFerias( ) );
		
		checkNNValues( entity );
		return persistence.update( entity );
	}
	
	/**
	 * Deletes an instance o f Artesano from the Data Base.
	 *
	 * @param id Identifier of the instance to remove.
	 */
	public void deleteArtesano( Long id )
	{
		persistence.delete( id );
	}
	
	private void checkNNValues( ArtesanoEntity entity ) throws BusinessLogicException
	{
		boolean nombre = entity.getNombre( ) == null || entity.getNombre( ).isEmpty( );
		boolean ident = entity.getIdentificacion( ) == null || entity.getIdentificacion( ).isEmpty( );
		boolean city = entity.getCiudad( ) == null;
		if( nombre || ident || city )
		{
			throw new BusinessLogicException( String.format( "%s del Artesano no puede estar vacío", nombre ? "El nombre" : ident ? "La identificacion" : "La ciudad " ), Response.Status.BAD_REQUEST );
		}
	}
}